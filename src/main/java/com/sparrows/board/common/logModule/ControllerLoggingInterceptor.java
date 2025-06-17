package com.sparrows.board.common.logModule;

import com.sparrows.board.common.logModule.enums.TraceHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ControllerLoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            //이전 요청의 cid를 현재 요청의 pid로 전환
            String tid = Optional.ofNullable(request.getHeader(TraceHeader.X_TRACE_ID.key())).orElse(UUID.randomUUID().toString());
            String pid = Optional.ofNullable(request.getHeader(TraceHeader.X_SPAN_ID.key())).orElse(TraceHeader.ROOT.key());
            String cid = UUID.randomUUID().toString(); // 현재 서비스 기준 새로운 CID
            String className = handlerMethod.getBeanType().getSimpleName();
            String method = handlerMethod.getMethod().getName();

            request.setAttribute(TraceHeader.X_TRACE_ID.key(), tid);
            request.setAttribute(TraceHeader.X_PARENT_SPAN_ID.key(), pid);
            request.setAttribute(TraceHeader.X_SPAN_ID.key(), cid);

            MDC.put(TraceHeader.X_TRACE_ID.key(), tid);
            MDC.put(TraceHeader.X_PARENT_SPAN_ID.key(), pid);
            MDC.put(TraceHeader.X_SPAN_ID.key(), cid);
            MDC.put(TraceHeader.CLAZZ.key(), className);
            MDC.put(TraceHeader.METHOD.key(), method);
            MDC.put(TraceHeader.FLOW.key(), "IN");

            log.info("REQUEST START");
            return true;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod handlerMethod) {
            MDC.put(TraceHeader.FLOW.key(), "OUT");
            log.info("REQUEST END");
        }
    }
}