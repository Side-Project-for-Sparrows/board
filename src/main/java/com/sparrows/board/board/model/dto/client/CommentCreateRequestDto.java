package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import lombok.Data;

@Data
public class CommentCreateRequestDto {
    private Long userId;
    private Long postId;
    private String content;

    public CommentEntity to() {
        return CommentEntity.builder()
                .userId(userId)
                .post(PostEntity.builder().id(postId).build())
                .content(content)
                .build();
    }
}