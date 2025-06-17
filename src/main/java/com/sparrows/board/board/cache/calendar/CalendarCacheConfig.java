package com.sparrows.board.board.cache.calendar;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.board.model.entity.CalendarEntity;
import com.sparrows.board.cache.factory.CacheManagerFactory;
import com.sparrows.board.cache.key.CacheKeyGenerator;
import com.sparrows.board.cache.layer.MultiLevelCacheManager;
import com.sparrows.board.cache.service.CacheTtlService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class CalendarCacheConfig {

    private final CacheTtlService cacheTtlService;

    @Bean
    public MultiLevelCacheManager<CalendarCacheKey, List<CalendarEntity>> calendarCacheManager(
            RedisTemplate<String, String> redisTemplate,
            ObjectMapper objectMapper
    ) {
        // calendar:5:2025-06 같은 key 생성
        CacheKeyGenerator<CalendarCacheKey> keyGenerator = new CalendarKeyGenerator();

        // List<CalendarEntity> → JSON
        Function<List<CalendarEntity>, String> serializer = list -> {
            try {
                return objectMapper.writeValueAsString(list);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        // JSON → List<CalendarEntity>
        Function<String, List<CalendarEntity>> deserializer = json -> {
            try {
                JavaType type = objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, CalendarEntity.class);
                return objectMapper.readValue(json, type);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        // 도메인별 TTL 조회
        var ttl = cacheTtlService.getDomainTtl("calendar");

        return CacheManagerFactory.create(
                ttl.getInMemory(),
                ttl.getRedis(),
                redisTemplate,
                keyGenerator,
                serializer,
                deserializer
        );
    }
}
