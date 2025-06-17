package com.sparrows.board.board.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.board.model.dto.internal.PostSaveRequest;
import com.sparrows.board.board.port.out.PostEventPort;
import com.sparrows.board.kafka.outbox.OutboxEvent;
import com.sparrows.board.kafka.outbox.OutboxEventRepository;
import com.sparrows.board.kafka.payload.board.PostCreatedPayload;
import com.sparrows.board.kafka.payload.board.PostEventEnum;
import com.sparrows.board.kafka.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class PostEventdapter implements PostEventPort {
    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;
    private final OutboxEventRepository outboxEventRepository;

    @Transactional
    public void publishPostCreatedEvent(PostSaveRequest request) {
        PostCreatedPayload payload = new PostCreatedPayload();
        payload.setPostId(request.getPostId());
        payload.setBoardId(request.getBoardId());
        payload.setTitle(request.getTitle());
        payload.setContent(request.getContent());
        payload.setWriterId(request.getWriterId());

        String json = null;
        try {
            json = objectMapper.writeValueAsString(payload); // Object → JSON 문자열
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }

        OutboxEvent outboxEvent = new OutboxEvent();

        outboxEvent.setAggregateType(kafkaProperties.getAggregateType().getPost());
        outboxEvent.setAggregateId(String.valueOf(payload.getPostId()));
        outboxEvent.setEventType(PostEventEnum.PostCreated.toString());
        outboxEvent.setTopic(kafkaProperties.getTopic().getPost().getCreate());
        outboxEvent.setKey(String.valueOf(payload.getPostId()));
        outboxEvent.setPayload(json);

        outboxEventRepository.save(outboxEvent);
    }
}
