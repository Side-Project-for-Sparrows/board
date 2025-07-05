package com.sparrows.board.board.model.dto.client;

import lombok.Data;

@Data
public class LikeResponseDto {
    String result;

    public LikeResponseDto(Boolean isSuccess){
        this.result = isSuccess ? "SUCCESS" : "FAIL";
    }
}
