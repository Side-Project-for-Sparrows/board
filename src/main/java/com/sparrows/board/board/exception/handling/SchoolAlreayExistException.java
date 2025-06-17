package com.sparrows.board.board.exception.handling;

import com.sparrows.board.board.exception.BoardErrorCode;
import com.sparrows.board.board.exception.BusinessException;

public class SchoolAlreayExistException extends BusinessException {
    public SchoolAlreayExistException() {
        super(BoardErrorCode.FAIL_ALREAY_EXIST_SCHOOL_NAME);
    }
}