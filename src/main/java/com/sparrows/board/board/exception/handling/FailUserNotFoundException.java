package com.sparrows.board.board.exception.handling;

import com.sparrows.board.board.exception.BoardErrorCode;
import com.sparrows.board.board.exception.BusinessException;

public class FailUserNotFoundException extends BusinessException {
    public FailUserNotFoundException() {
        super(BoardErrorCode.FAIL_USER_NOT_FOUND);
    }
}