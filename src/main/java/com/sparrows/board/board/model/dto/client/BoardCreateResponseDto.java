package com.sparrows.board.board.model.dto.client;

import lombok.Getter;

@Getter
public class BoardCreateResponseDto {
    String result;
    String enterCode;

    public BoardCreateResponseDto(Boolean isSuccess, String enterCode){
        this.result = isSuccess ? "SUCCESS" : "FAIL";
        this.enterCode = enterCode;
    }
}
