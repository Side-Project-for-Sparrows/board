package com.sparrows.board.board.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.board.port.in.UserBoardUsecase;
import com.sparrows.board.kafka.payload.user.UserCreatedPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListener {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    UserBoardUsecase userBoardUsecase;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000, multiplier = 2),
            dltTopicSuffix = ".dlt",
            autoCreateTopics = "true"
    )
    @KafkaListener(topics = "${kafka.topic.user.create}")
    public void handleUserEvent(String message) throws JsonProcessingException {
        UserCreatedPayload createdPayload = objectMapper.readValue(message, UserCreatedPayload.class);
        log.info("user created payload: {}", createdPayload);
        handleUserCreated(createdPayload);
    }

    @KafkaListener(topics = "${kafka.topic.user.create}.dlt")
    public void handleDlt(String message) {
        // 저장, 알림, 재처리 로직 등
        log.error("DLT 메시지 수신: {}", message);
    }

    private void handleUserCreated(UserCreatedPayload payload) {
        userBoardUsecase.handleUserCreated(payload.getUserId(),payload.getNickname(), payload.getSchoolId(), payload.getUserType());
    }
}
