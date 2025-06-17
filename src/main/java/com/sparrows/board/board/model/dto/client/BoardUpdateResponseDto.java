package com.sparrows.board.board.model.dto.client;

import lombok.Getter;

@Getter
public class BoardUpdateResponseDto {
    String result;

    public BoardUpdateResponseDto(Boolean isSuccess){
        this.result = isSuccess ? "SUCCESS" : "FAIL";
    }
}
