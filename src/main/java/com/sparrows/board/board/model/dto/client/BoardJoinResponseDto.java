package com.sparrows.board.board.model.dto.client;

import lombok.Getter;

@Getter
public class BoardJoinResponseDto {
    String result;

    public BoardJoinResponseDto(Boolean isSuccess){
        this.result = isSuccess ? "SUCCESS" : "FAIL";
    }
}