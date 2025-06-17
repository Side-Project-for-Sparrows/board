package com.sparrows.board.kafka.outbox;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "outbox_event")
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;
    private String aggregateId;

    private String eventType;

    //@Column(columnDefinition = "jsonb")
    private String payload;

    private String topic;
    private String key;

    private String status = "PENDING";

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime sentAt;
    private int retryCount = 0;

    public OutboxEvent(){
        this.status = "PENDING";
    }
}
