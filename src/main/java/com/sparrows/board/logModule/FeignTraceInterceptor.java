package com.sparrows.board.logModule;

import com.sparrows.board.logModule.enums.TraceHeader;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class FeignTraceInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return;

        HttpServletRequest request = attrs.getRequest();

        String tid = Optional.ofNullable((String) request.getAttribute(TraceHeader.X_TRACE_ID.key())).orElse(UUID.randomUUID().toString());
        String cid = Optional.ofNullable((String) request.getAttribute(TraceHeader.X_SPAN_ID.key())).orElseThrow();

        template.header(TraceHeader.X_TRACE_ID.key(), tid);
        template.header(TraceHeader.X_SPAN_ID.key(), cid); //현재 cid를 그대로 던지면, 받는곳에서 pid로 전환할것.
        log.info("REQUEST PROPAGATION");
    }
}