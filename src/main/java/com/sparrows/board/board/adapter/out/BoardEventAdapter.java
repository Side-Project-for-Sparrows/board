package com.sparrows.board.board.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.board.feignclient.BoardIndexClient;
import com.sparrows.board.board.model.dto.internal.BoardSaveRequest;
import com.sparrows.board.board.port.out.BoardEventPort;
import com.sparrows.board.kafka.outbox.OutboxEvent;
import com.sparrows.board.kafka.outbox.OutboxEventRepository;
import com.sparrows.board.kafka.payload.board.BoardCreatedPayload;
import com.sparrows.board.kafka.payload.board.PostEventEnum;
import com.sparrows.board.kafka.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class BoardEventAdapter implements BoardEventPort {

    private final BoardIndexClient boardIndexClient;
    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;
    private final OutboxEventRepository outboxEventRepository;
    @Transactional
    public void publishBoardCreateEvent(BoardSaveRequest request) {
        BoardCreatedPayload payload = new BoardCreatedPayload();
        payload.setBoardId(request.getBoardId());
        payload.setSchoolId(request.getSchoolId());
        payload.setName(request.getName());
        payload.setDescription(request.getDescription());

        String json = null;
        try {
            json = objectMapper.writeValueAsString(payload); // Object → JSON 문자열
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setAggregateType(kafkaProperties.getAggregateType().getPost());
        outboxEvent.setAggregateId(String.valueOf(payload.getBoardId()));
        outboxEvent.setEventType(PostEventEnum.PostCreated.toString());
        outboxEvent.setTopic(kafkaProperties.getTopic().getBoard().getCreate());
        outboxEvent.setKey(String.valueOf(payload.getBoardId()));
        outboxEvent.setPayload(json);

        outboxEventRepository.save(outboxEvent);
    }
}
