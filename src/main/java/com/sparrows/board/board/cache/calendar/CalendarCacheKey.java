package com.sparrows.board.board.cache.calendar;

import java.time.YearMonth;

public record CalendarCacheKey(Long boardId, YearMonth yearMonth) {
    @Override
    public String toString() {
        return boardId + ":" + yearMonth;
    }
}

