package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CommentDetailDto {
    Long id;
    Long userId;
    String nickname;
    String content;
    Integer likeCount;

    public CommentDetailDto(CommentEntity entity){
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.content = entity.getContent();
        this.likeCount = entity.getLikeCount();
    }
}
