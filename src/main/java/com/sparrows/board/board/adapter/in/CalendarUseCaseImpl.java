package com.sparrows.board.board.adapter.in;

import com.sparrows.board.board.cache.calendar.CalendarCacheKey;
import com.sparrows.board.board.cache.calendar.CalendarKeyGenerator;
import com.sparrows.board.board.exception.handling.AccessDeniedException;
import com.sparrows.board.board.model.dto.client.*;
import com.sparrows.board.board.model.entity.CalendarEntity;
import com.sparrows.board.board.port.in.CalendarUseCase;
import com.sparrows.board.board.port.out.CalendarPort;
import com.sparrows.board.board.port.out.UserBoardRelationPort;
import com.sparrows.board.cache.layer.MultiLevelCacheManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarUseCaseImpl implements CalendarUseCase {

    private final CalendarPort calendarPort;
    private final UserBoardRelationPort userBoardPort;
    private final MultiLevelCacheManager<CalendarCacheKey, List<CalendarEntity>> cacheManager;
    private final CalendarKeyGenerator calendarKeyGenerator;

    @Override
    @Transactional(readOnly = true)
    public List<CalendarResponseDto> getCalendarList(GetCalendarRequestDto request) {
        validateUserAccess(request.getUserId(), request.getBoardId());

        CalendarCacheKey key = new CalendarCacheKey(
                request.getBoardId().longValue(),
                YearMonth.of(request.getYear(), request.getMonth())
        );

        List<CalendarEntity> entities = cacheManager.getOrLoad(key, k -> {
            log.info("[DB LOAD] boardId={}, year={}, month={}", request.getBoardId(), request.getYear(), request.getMonth());
            return calendarPort.findByMonth(request.getBoardId(), key.yearMonth());
        });

        return entities.stream()
                .map(CalendarResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void upsertCalendar(UpsertCalendarRequestDto request) {
        validateUserAccess(request.getUserId(), request.getBoardId());

        calendarPort.saveOrUpdate(request);

        evictCache(request.getBoardId(), request.getTime().getYear(), request.getTime().getMonthValue());
    }

    @Override
    @Transactional
    public void deleteCalendar(DeleteCalendarRequestDto request) {
        validateUserAccess(request.getUserId(), request.getBoardId());

        YearMonth yearMonth = calendarPort.findYearMonthById(request.getId(), request.getBoardId());

        if (yearMonth != null) {
            calendarPort.delete(request.getId(), request.getBoardId());
            evictCache(request.getBoardId(), yearMonth.getYear(), yearMonth.getMonthValue());
        }
    }

    private void evictCache(Integer boardId, int year, int month) {
        CalendarCacheKey key = new CalendarCacheKey(
                boardId.longValue(),
                YearMonth.of(year, month)
        );
        cacheManager.evict(key);
        log.info("[Cache Evicted] key={}", calendarKeyGenerator.generateKey(key));
    }

    private void validateUserAccess(Long userId, Integer boardId) {
        if (!userBoardPort.isUserInBoard(userId, boardId)) {
            throw new AccessDeniedException();
        }
    }
}
