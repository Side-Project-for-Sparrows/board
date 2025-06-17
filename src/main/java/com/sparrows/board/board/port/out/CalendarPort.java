package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.dto.client.UpsertCalendarRequestDto;
import com.sparrows.board.board.model.entity.CalendarEntity;

import java.time.YearMonth;
import java.util.List;

public interface CalendarPort {
    void saveOrUpdate(UpsertCalendarRequestDto request);
    void delete(Long id, Integer boardId);
    List<CalendarEntity> findByMonth(Integer boardId, YearMonth ym);
    YearMonth findYearMonthById(Long id, Integer boardId);
}