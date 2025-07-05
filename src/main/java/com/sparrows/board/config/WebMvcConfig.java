package com.sparrows.board.config;

import com.sparrows.board.logModule.ControllerLoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ControllerLoggingInterceptor controllerLoggingInterceptor;

    public WebMvcConfig(ControllerLoggingInterceptor controllerLoggingInterceptor) {
        this.controllerLoggingInterceptor = controllerLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerLoggingInterceptor)
                .addPathPatterns("/**"); // 모든 경로에 적용
    }
}