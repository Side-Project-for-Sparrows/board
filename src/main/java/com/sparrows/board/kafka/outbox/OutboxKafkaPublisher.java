package com.sparrows.board.kafka.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Component
public class OutboxKafkaPublisher {
    private final OutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 10000) // 1초마다 수행
    public void publishPendingEvents() {
        List<OutboxEvent> pendingEvents = outboxRepository.findTop50ByStatusOrderByCreatedAtAsc("PENDING");

        for (OutboxEvent event : pendingEvents) {
            try {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(event.getTopic(), event.getKey(), event.getPayload());

                future.thenAccept(result -> {
                    // Kafka 전송 성공 시
                    event.setStatus("SENT");
                    event.setSentAt(LocalDateTime.now());
                    outboxRepository.save(event);
                }).exceptionally(ex -> {
                    // Kafka 전송 실패 시
                    event.setRetryCount(event.getRetryCount() + 1);
                    if (event.getRetryCount() > 5) {
                        event.setStatus("FAILED");
                    }
                    outboxRepository.save(event);
                    return null;
                });
            } catch (Exception e) {
                log.error("Kafka 전송 실패: {}", event.getId(), e);
                event.setRetryCount(event.getRetryCount() + 1);

                if (event.getRetryCount() > 5) {
                    event.setStatus("FAILED");
                }
                outboxRepository.save(event);
            }
        }
    }
}
