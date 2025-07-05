package com.sparrows.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode {
    // Global
    INVALID_REQUEST(HttpStatus.UNPROCESSABLE_ENTITY, "INVALID REQUEST ERROR"),
    INTERNAL_SERVER(HttpStatus.UNPROCESSABLE_ENTITY, "INTERNAL SERVER ERROR"),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "FAIL TO FIND COMMENT"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "FAIL TO FIND POST"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "FAIL TO FIND BOARD"),
    BOARD_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "FAIL TO FIND BOARD USER"),
    FAIL_USER_CREATE_BOARD(HttpStatus.BAD_GATEWAY, "FAIL TO CREATE BOARD BY USER"),
    FAIL_BOARD_API_INTEGRATION(HttpStatus.BAD_GATEWAY, "FAIL TO INTEGRATE WITH BOARD API"),
    FAIL_USER_NOT_FOUND(HttpStatus.BAD_GATEWAY, "FAIL TO FOUND USER RELATED WITH BOARD"),
    SCHOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "School not found"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access is denied."),
    FAIL_ALREAY_EXIST_SCHOOL_NAME(HttpStatus.BAD_GATEWAY, "SCHOOL NAME ALREADY EXISTS");

    private final HttpStatus status;
    private final String message;
}
