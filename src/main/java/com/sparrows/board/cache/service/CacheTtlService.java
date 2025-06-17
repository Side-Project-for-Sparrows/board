package com.sparrows.board.cache.service;

import com.sparrows.board.cache.config.CacheTtlProperties;
import com.sparrows.board.cache.config.CacheTtlProperties.DomainTtl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CacheTtlService {

    private final CacheTtlProperties cacheTtlProperties;

    public DomainTtl getDomainTtl(String domainName) {
        // 도메인 값 있으면 그대로 반환
        DomainTtl ttl = cacheTtlProperties.getDomain().get(domainName);
        if (ttl != null) return ttl;

        // 도메인별 TTL이 없을 경우, 기본 TTL 대체
        Duration defaultDuration = Duration.parse("PT" + cacheTtlProperties.getDefaultTtl());
        DomainTtl defaultTtl = new DomainTtl();
        defaultTtl.setInMemory(defaultDuration);
        defaultTtl.setRedis(defaultDuration);
        return defaultTtl;
    }
}