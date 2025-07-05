package com.sparrows.board.exception.handling;

import com.sparrows.board.exception.BoardErrorCode;
import com.sparrows.board.exception.BusinessException;

public class PostNotFouncException extends BusinessException {
    public PostNotFouncException() {
        super(BoardErrorCode.POST_NOT_FOUND);
    }
}
