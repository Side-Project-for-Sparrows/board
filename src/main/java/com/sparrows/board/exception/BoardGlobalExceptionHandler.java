package com.sparrows.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@RestControllerAdvice("com.sparrows.board.board")
public class BoardGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BoardErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        final BoardErrorResponse errorResponse = BoardErrorResponse.of(e);
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<BoardErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        final BoardErrorResponse errorResponse = BoardErrorResponse.of(BoardErrorCode.INTERNAL_SERVER);
        return new ResponseEntity<>(errorResponse, BoardErrorCode.INTERNAL_SERVER.getStatus());
    }


    @ExceptionHandler({
            MissingServletRequestPartException.class,
            MissingRequestValueException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<BoardErrorResponse> handleRequestException(Exception e) {
        log.error(e.getMessage(), e);
        final BoardErrorResponse errorResponse = BoardErrorResponse.of(BoardErrorCode.INVALID_REQUEST);
        return new ResponseEntity<>(errorResponse, BoardErrorCode.INVALID_REQUEST.getStatus());
    }

}

