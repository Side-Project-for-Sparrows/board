package com.sparrows.board.exception.handling;

import com.sparrows.board.exception.BoardErrorCode;
import com.sparrows.board.exception.BusinessException;

public class BoardNotFouncException extends BusinessException {
    public BoardNotFouncException() {
        super(BoardErrorCode.BOARD_NOT_FOUND);
    }
}
