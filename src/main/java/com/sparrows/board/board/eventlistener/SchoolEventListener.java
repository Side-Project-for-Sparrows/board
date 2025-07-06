package com.sparrows.board.board.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.port.in.BoardUsecase;
import com.sparrows.board.kafka.payload.school.SchoolCreatedPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchoolEventListener {
    @Autowired
    BoardUsecase boardUsecase;

    @Autowired
    ObjectMapper objectMapper;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000, multiplier = 2),
            dltTopicSuffix = ".dlt",
            autoCreateTopics = "true"
    )
    @KafkaListener(topics = "${kafka.topic.school.create}")
    public void handleSchoolEvent(String message) throws JsonProcessingException {
        SchoolCreatedPayload payload = objectMapper.readValue(message, SchoolCreatedPayload.class);

        BoardEntity entity = new BoardEntity();
        entity.setSchoolId(payload.getSchoolId());
        entity.setName(payload.getSchoolName() + "게시판");
        entity.setDescription(payload.getSchoolName() + "게시판");
        entity.setIsPublic(true);
        entity.setMadeBy(0L);

        boardUsecase.createNewBoard(0L, entity);
    }

    @KafkaListener(topics = "${kafka.topic.school.create}.dlt", groupId = "${kafka.groupId.board}")
    public void handleDlt(String message) {
        log.error("DLT 메시지 수신: {}", message);
        // 저장, 알림, 재처리 로직 등
    }

}
