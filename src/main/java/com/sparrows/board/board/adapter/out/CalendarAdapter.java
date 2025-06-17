package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.model.dto.client.UpsertCalendarRequestDto;
import com.sparrows.board.board.model.entity.CalendarEntity;
import com.sparrows.board.board.adapter.repository.CalendarRepository;
import com.sparrows.board.board.port.out.CalendarPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalendarAdapter implements CalendarPort {

    private final CalendarRepository calendarRepository;

    @Override
    public void saveOrUpdate(UpsertCalendarRequestDto request) {
        CalendarEntity entity = CalendarEntity.builder()
                .id(request.getId())
                .title(request.getTitle())
                .memo(request.getMemo())
                .boardId(request.getBoardId())
                .time(request.getTime())
                .build();
        calendarRepository.save(entity);
    }

    @Override
    public void delete(Long id, Integer boardId) {
        calendarRepository.deleteByIdAndBoardId(id, boardId);
    }

    @Override
    public List<CalendarEntity> findByMonth(Integer boardId, YearMonth ym) {
        return calendarRepository.findByBoardIdAndTimeBetween(
                boardId,
                ym.atDay(1).atStartOfDay().atOffset(java.time.ZoneOffset.UTC),
                ym.atEndOfMonth().atTime(23, 59, 59).atOffset(java.time.ZoneOffset.UTC)
        );
    }

    @Override
    public YearMonth findYearMonthById(Long id, Integer boardId) {
        return calendarRepository.findByIdAndBoardId(id, boardId)
                .map(entity -> YearMonth.from(entity.getTime()))
                .orElse(null);
    }
}