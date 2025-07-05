package com.sparrows.board.exception.handling;

import com.sparrows.board.exception.BoardErrorCode;
import com.sparrows.board.exception.BusinessException;

public class CommentNotFouncException extends BusinessException {
    public CommentNotFouncException() {
        super(BoardErrorCode.COMMENT_NOT_FOUND);
    }
}
