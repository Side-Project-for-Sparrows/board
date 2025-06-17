package com.sparrows.board.cache.layer;

import com.sparrows.board.cache.key.CacheKeyGenerator;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class InMemoryCacheLayer<K, V> implements CacheLayer<K, V> {

    private final Map<String, CacheItem<V>> store = new ConcurrentHashMap<>();
    private final Duration ttl;
    private final CacheKeyGenerator<K> keyGenerator;

    // 백그라운드 만료 청소 스레드
     private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    public InMemoryCacheLayer(Duration ttl, CacheKeyGenerator<K> keyGenerator) {
        this.ttl = ttl;
        this.keyGenerator = keyGenerator;

        cleaner.scheduleAtFixedRate(this::cleanupExpiredEntries, 5, 5, TimeUnit.MINUTES);
    }

    @Override
    public V get(K key) {
        String cacheKey = keyGenerator.generateKey(key);
        CacheItem<V> item = store.get(cacheKey);
        // 값 없을때
        if (item == null) {
            log.info("[InMemory MISS] key={}", cacheKey);
            return null;
        }
        // 값 만료일때
        if (item.isExpired()) {
            store.remove(cacheKey);
            log.info("[InMemory EXPIRED] key={}", cacheKey);
            return null;
        }
        log.info("[InMemory HIT] key={}", cacheKey);
        return item.value;
    }

    @Override
    public void put(K key, V value) {
        String cacheKey = keyGenerator.generateKey(key);
        store.put(cacheKey, new CacheItem<>(value, ttl));
    }

    @Override
    public void evict(K key) {
        String cacheKey = keyGenerator.generateKey(key);
        store.remove(cacheKey);
    }

    @Override
    public void refreshTtl(K key) {
        String cacheKey = keyGenerator.generateKey(key);
        CacheItem<V> item = store.get(cacheKey);
        if (item != null && !item.isExpired()) {
            item.refresh(ttl);
            log.info("[InMemory REFRESH TTL] key={}", cacheKey);
        }
    }

    private void cleanupExpiredEntries() {
        int removedCount = 0;
        for (var entry : store.entrySet()) {
            if (entry.getValue().isExpired()) {
                store.remove(entry.getKey());
                removedCount++;
            }
        }
        if (removedCount > 0) {
            log.info("[InMemoryCacheCleaner] removed {} expired entries", removedCount);
        }
    }

//     애플리케이션 종료 시 호출 가능
     @PreDestroy
     public void shutdown() {
         cleaner.shutdown();
     }

    private static class CacheItem<V> {
        private V value;
        private Instant expireAt;

        CacheItem(V value, Duration ttl) {
            this.value = value;
            this.expireAt = Instant.now().plus(ttl);
        }

        boolean isExpired() {
            return Instant.now().isAfter(expireAt);
        }

        void refresh(Duration ttl) {
            this.expireAt = Instant.now().plus(ttl);
        }
    }
}
