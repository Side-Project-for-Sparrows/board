package com.sparrows.board.board.model.dto.client;

import lombok.Getter;

@Getter
public class PostCreateResponseDto {
    String result;

    public PostCreateResponseDto(Boolean isSuccess){
        this.result = isSuccess ? "SUCCESS" : "FAIL";
    }
}
