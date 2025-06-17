package com.sparrows.board.board.model.dto.client;

import lombok.Getter;

@Getter
public class BoardDeleteResponseDto {
    String result;

    public BoardDeleteResponseDto(Boolean isSuccess){
        this.result = isSuccess ? "SUCCESS" : "FAIL";
    }
}
