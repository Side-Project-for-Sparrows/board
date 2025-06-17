package com.sparrows.board.security.exception.handling;

import com.sparrows.board.security.exception.BusinessException;
import com.sparrows.board.security.exception.SecurityErrorCode;

public class JwtAuthenticationException extends BusinessException {

    public JwtAuthenticationException(SecurityErrorCode errorCode) {
        super(errorCode);
    }

}
