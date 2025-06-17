package com.sparrows.board.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode {
    // Global
    INVALID_REQUEST(HttpStatus.UNPROCESSABLE_ENTITY, "INVALID REQUEST ERROR"),
    INTERNAL_SERVER(HttpStatus.UNPROCESSABLE_ENTITY, "INTERNAL SERVER ERROR"),

    // JWT Errors
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Access token has expired"),
    ACCESS_TOKEN_UNEXPECTED(HttpStatus.UNAUTHORIZED, "Access token is invalid or unexpected"),
    TOKEN_GENERATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate access token"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found");

    private final HttpStatus status;
    private final String message;
}
