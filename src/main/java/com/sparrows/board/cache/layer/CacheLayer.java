package com.sparrows.board.cache.layer;

public interface CacheLayer<K, V> {
    V get(K key); // 캐시에서 값을 조회
    void put(K key, V value); // 캐시에 값을 저장
    void evict(K key); // 캐시에서 값을 제거(무효화)
    void refreshTtl(K key); // TTL(만료시간)을 갱신
}