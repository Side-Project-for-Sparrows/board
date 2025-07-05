package com.sparrows.board.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardErrorResponse {

    private final int status;
    private final String message;

    private BoardErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static BoardErrorResponse of(BusinessException e) {
        return BoardErrorResponse.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }

    public static BoardErrorResponse of(BoardErrorCode e) {
        return BoardErrorResponse.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }
}

