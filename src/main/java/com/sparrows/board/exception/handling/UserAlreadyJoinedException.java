package com.sparrows.board.exception.handling;

import com.sparrows.board.exception.BoardErrorCode;
import com.sparrows.board.exception.BusinessException;

public class UserAlreadyJoinedException extends BusinessException {
    public UserAlreadyJoinedException() {
        super(BoardErrorCode.USER_ALREADY_JOINED);
    }
}
