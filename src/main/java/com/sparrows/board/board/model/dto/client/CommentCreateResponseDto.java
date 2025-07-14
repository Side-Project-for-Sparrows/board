package com.sparrows.board.board.model.dto.client;

import lombok.Data;

@Data
public class CommentCreateResponseDto {
    boolean success;

    public CommentCreateResponseDto(Boolean isSuccess) {
        this.success = isSuccess;
    }
}