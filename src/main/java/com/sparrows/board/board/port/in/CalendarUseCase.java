package com.sparrows.board.board.port.in;

import com.sparrows.board.board.model.dto.client.*;

import java.util.List;

public interface CalendarUseCase {
    void upsertCalendar(UpsertCalendarRequestDto request);
    void deleteCalendar(DeleteCalendarRequestDto request);
    List<CalendarResponseDto> getCalendarList(GetCalendarRequestDto request);
}