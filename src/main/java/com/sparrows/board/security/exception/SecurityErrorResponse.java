package com.sparrows.board.security.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SecurityErrorResponse {

    private final int status;
    private final String message;

    private SecurityErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static SecurityErrorResponse of(BusinessException e) {
        return SecurityErrorResponse.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }

    public static SecurityErrorResponse of(SecurityErrorCode e) {
        return SecurityErrorResponse.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }
}

