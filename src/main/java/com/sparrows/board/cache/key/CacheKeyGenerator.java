package com.sparrows.board.cache.key;

@FunctionalInterface
public interface CacheKeyGenerator<K> {
    String generateKey(K key);
}