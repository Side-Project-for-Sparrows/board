package com.sparrows.board.board.cache.calendar;


import com.sparrows.board.cache.key.CacheKeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class CalendarKeyGenerator implements CacheKeyGenerator<CalendarCacheKey> {

    @Override
    public String generateKey(CalendarCacheKey key) {
        return "calendar:" + key.toString();  // 결과: calendar:5:2025-06
    }
}