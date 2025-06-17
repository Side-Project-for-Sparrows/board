package com.sparrows.board.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "cache.ttl") // application.yml에 있는 cache.ttl 설정을 객체로 바인딩
@Getter
@Setter
public class CacheTtlProperties {

    private Duration defaultTtl;
    private Map<String, DomainTtl> domain = new HashMap<>();

    @Getter
    @Setter
    public static class DomainTtl {
        private Duration inMemory;
        private Duration redis;
    }
}
