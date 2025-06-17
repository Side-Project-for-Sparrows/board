package com.sparrows.board.board.exception.handling;

import com.sparrows.board.board.exception.BoardErrorCode;
import com.sparrows.board.board.exception.BusinessException;

public class PostNotFouncException extends BusinessException {
    public PostNotFouncException() {
        super(BoardErrorCode.POST_NOT_FOUND);
    }
}
