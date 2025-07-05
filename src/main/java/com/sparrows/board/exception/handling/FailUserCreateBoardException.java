package com.sparrows.board.exception.handling;

import com.sparrows.board.exception.BusinessException;
import com.sparrows.board.exception.BoardErrorCode;

public class FailUserCreateBoardException extends BusinessException {
    public FailUserCreateBoardException() {
        super(BoardErrorCode.FAIL_USER_CREATE_BOARD);
    }
}
