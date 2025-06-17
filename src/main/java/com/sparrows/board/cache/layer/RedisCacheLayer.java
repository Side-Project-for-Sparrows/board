package com.sparrows.board.cache.layer;

import com.sparrows.board.cache.key.CacheKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Function;

@Slf4j
public class RedisCacheLayer<K, V> implements CacheLayer<K, V> {

    private final RedisTemplate<String, String> redisTemplate;
    private final Duration ttl;
    private final CacheKeyGenerator<K> keyGenerator;

    // 값 객체(V)를 문자열로 직렬화/역직렬화하는 함수
    private final Function<V, String> serializer;
    private final Function<String, V> deserializer;

    // Lua 스크립트를 통한 원자적 get-or-set 처리를 위한 RedisScript 객체
    private final DefaultRedisScript<String> getOrSetScript;

    public RedisCacheLayer(
            RedisTemplate<String, String> redisTemplate,
            Duration ttl,
            CacheKeyGenerator<K> keyGenerator,
            Function<V, String> serializer,
            Function<String, V> deserializer) {
        this.redisTemplate = redisTemplate;
        this.ttl = ttl;
        this.keyGenerator = keyGenerator;
        this.serializer = serializer;
        this.deserializer = deserializer;

        this.getOrSetScript = new DefaultRedisScript<>();
        this.getOrSetScript.setScriptText(buildLuaScript());
        this.getOrSetScript.setResultType(String.class);
    }

    @Override
    public V get(K key) {
        String redisKey = keyGenerator.generateKey(key);
        String json = redisTemplate.opsForValue().get(redisKey);
        if (json == null) {
            log.info("[Redis MISS] key={}", redisKey);
            return null;
        }

        log.info("[Redis HIT] key={}", redisKey);
        return deserializer.apply(json);
    }

    @Override
    public void put(K key, V value) {
        String redisKey = keyGenerator.generateKey(key);
        String json = serializer.apply(value);
        redisTemplate.opsForValue().set(redisKey, json, ttl);
    }

    @Override
    public void evict(K key) {
        String redisKey = keyGenerator.generateKey(key);
        redisTemplate.delete(redisKey);
    }

    @Override
    public void refreshTtl(K key) {
        String redisKey = keyGenerator.generateKey(key);
        redisTemplate.expire(redisKey, ttl);
        log.info("[Redis REFRESH TTL] key={}", redisKey);
    }

    // Lua 스크립트 이용한 get-or-set 원자성 처리
    public V getOrLoadWithLua(K key, Function<K, V> dbLoader) {
        String redisKey = keyGenerator.generateKey(key);
        String ttlSeconds = String.valueOf(ttl.getSeconds());

        V value = dbLoader.apply(key);
        if (value == null) return null;

        String json = serializer.apply(value);

        // Lua 스크립트를 실행하여 Redis에서 값을 조회하거나 새로 저장
        String result = redisTemplate.execute(
                getOrSetScript,
                Collections.singletonList(redisKey),
                ttlSeconds,
                json
        );

        return result != null ? deserializer.apply(result) : null;
    }

    private String buildLuaScript() {
        return """
            local existing = redis.call('GET', KEYS[1])
            if existing then
                redis.call('EXPIRE', KEYS[1], ARGV[1])
                return existing
            else
                redis.call('SET', KEYS[1], ARGV[2], 'EX', ARGV[1])
                return ARGV[2]
            end
        """;
    }
}
