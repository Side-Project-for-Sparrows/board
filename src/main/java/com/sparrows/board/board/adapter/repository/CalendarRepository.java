package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {
    void deleteByIdAndBoardId(Long id, Integer boardId);
    List<CalendarEntity> findByBoardIdAndTimeBetween(Integer boardId, OffsetDateTime start, OffsetDateTime end);
    Optional<CalendarEntity> findByIdAndBoardId(Long id, Integer boardId);
}
