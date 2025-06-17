package com.sparrows.board.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import static com.sparrows.board.security.exception.SecurityErrorCode.INTERNAL_SERVER;
import static com.sparrows.board.security.exception.SecurityErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice("com.sparrows.board.security")
public class SecurityGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<SecurityErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        final SecurityErrorResponse errorResponse = SecurityErrorResponse.of(e);
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<SecurityErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        final SecurityErrorResponse errorResponse = SecurityErrorResponse.of(INTERNAL_SERVER);
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER.getStatus());
    }


    @ExceptionHandler({
            MissingServletRequestPartException.class,
            MissingRequestValueException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<SecurityErrorResponse> handleRequestException(Exception e) {
        log.error(e.getMessage(), e);
        final SecurityErrorResponse errorResponse = SecurityErrorResponse.of(INVALID_REQUEST);
        return new ResponseEntity<>(errorResponse, INVALID_REQUEST.getStatus());
    }

}

