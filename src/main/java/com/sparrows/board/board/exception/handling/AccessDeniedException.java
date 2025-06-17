package com.sparrows.board.board.exception.handling;

import com.sparrows.board.board.exception.BusinessException;
import com.sparrows.board.board.exception.BoardErrorCode;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException() {
        super(BoardErrorCode.ACCESS_DENIED);
    }
}
