package com.sparrows.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID INPUT VALUE"),
    FAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "FAIL AUTHENTICATION"), // 로그인X 유저의 요청 OR 토큰 불일(?)
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN EXPIRED"), // 엑세스 토큰 만료
    FAIL_AUTHORIZATION(HttpStatus.FORBIDDEN, "FAIL AUTHORIZATION"), // 권한 없는 요청
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"), // 예상치 못한 에러
    TOKEN_GENERATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"TOKEN_GENERATE_FAILED"); //토큰 파싱중 에러 발생

    private final HttpStatus status;
    private final String message;
}