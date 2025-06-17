package com.sparrows.board.board.model.dto.client;

import lombok.Getter;

@Getter
public class BoardWithdrawResponseDto {
    String result;

    public BoardWithdrawResponseDto(Boolean isSuccess){
        this.result = isSuccess ? "SUCCESS" : "FAIL";
    }
}