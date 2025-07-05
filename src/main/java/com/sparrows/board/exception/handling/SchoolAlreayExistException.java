package com.sparrows.board.exception.handling;

import com.sparrows.board.exception.BoardErrorCode;
import com.sparrows.board.exception.BusinessException;

public class SchoolAlreayExistException extends BusinessException {
    public SchoolAlreayExistException() {
        super(BoardErrorCode.FAIL_ALREAY_EXIST_SCHOOL_NAME);
    }
}