package com.sparrows.board.kafka.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    // PENDING 이벤트 중 일정 수 조회
    List<OutboxEvent> findTop50ByStatusOrderByCreatedAtAsc(String status);
}