package com.sparrows.board.board.model.dto.client;

import lombok.Data;

@Data
public class CommentCreateResponseDto {
    String result;

    public CommentCreateResponseDto(Boolean isSuccess) {
        this.result = isSuccess ? "SUCCESS" : "FAIL";
    }
}