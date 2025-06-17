package com.sparrows.board.security.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.common.ErrorCode;
import com.sparrows.board.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;

public class ExceptionHandler implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer) {
        ObjectMapper objectMapper = new ObjectMapper();
        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(((request, response, authException) -> {
                    if (request.getAttribute("exception") != null) {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        objectMapper.writeValue(
                                response.getOutputStream(),
                                ErrorResponse.of((ErrorCode) request.getAttribute("exception"))
                        );
                    }
                }))
                // 인가예외 발생시 수행 메서드
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    objectMapper.writeValue(
                            response.getOutputStream(),
                            ErrorResponse.of(ErrorCode.FAIL_AUTHORIZATION)
                    );
                }));
    }
}
