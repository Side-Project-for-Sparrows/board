package com.sparrows.board.exception.handling;

import com.sparrows.board.exception.BoardErrorCode;
import com.sparrows.board.exception.BusinessException;

public class BoardUserNotFoundException extends BusinessException {
    public BoardUserNotFoundException() {
        super(BoardErrorCode.BOARD_USER_NOT_FOUND);
    }
}
