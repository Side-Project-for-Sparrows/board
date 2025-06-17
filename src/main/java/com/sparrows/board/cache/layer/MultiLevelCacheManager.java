package com.sparrows.board.cache.layer;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;

@Slf4j
public class MultiLevelCacheManager<K, V> {
    private final List<CacheLayer<K, V>> layers;

    public MultiLevelCacheManager(List<CacheLayer<K, V>> layers) {
        this.layers = layers;
    }

    public V getOrLoad(K key, Function<K, V> dbLoader) {
        // 계층별로 순서대로 get을 시도해서 캐시값을 찾음 (인메모리부터 Redis까지)
        for (int i = 0; i < layers.size(); i++) {
            CacheLayer<K, V> layer = layers.get(i);
            V value = layer.get(key);
            if (value != null) {
                layer.refreshTtl(key);
                cacheUpwards(key, value, i);
                return value;
            }
        }

        log.info("[DB LOAD] key={}", key); // 추가: 캐시 미스 후 DB 조회 로그

        // 모든 캐시 레이어에서 못 찾으면 DB에서 로드 시작
        V loaded = dbLoader.apply(key);
        if (loaded != null) {
            for (CacheLayer<K, V> layer : layers) {
                if (layer instanceof RedisCacheLayer<K, V> redisLayer) {
                    // Redis 캐시는 Lua 스크립트 기반 원자적 getOrLoadWithLua로 저장
                    redisLayer.getOrLoadWithLua(key, k -> loaded);
                } else {
                    // 그 외 캐시는 그냥 put 메서드로 저장
                    layer.put(key, loaded);
                }
            }
        }
        return loaded;
    }

    // 히트한 레이어 위쪽 계층에 값을 올려서 캐시 저장 ex) Redis에서 찾은 값을 인메모리에도 넣어줌
    private void cacheUpwards(K key, V value, int hitLayerIndex) {
        for (int i = 0; i < hitLayerIndex; i++) {
            layers.get(i).put(key, value);
        }
    }

    public void evict(K key) {
        for (CacheLayer<K, V> layer : layers) {
            layer.evict(key);
        }
    }
}