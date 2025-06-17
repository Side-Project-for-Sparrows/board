package com.sparrows.board.board.model.dto.client;

import lombok.Getter;

@Getter
public class UserBoardCreateResponseDto {
    String result;
    String enterCode;

    public UserBoardCreateResponseDto(Boolean isSuccess, String enterCode){
        this.result = isSuccess ? "SUCCESS" : "FAIL";
        this.enterCode = enterCode;
    }
}