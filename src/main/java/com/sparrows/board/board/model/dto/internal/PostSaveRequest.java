package com.sparrows.board.board.model.dto.internal;

import com.sparrows.board.board.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequest {
    Long postId;
    Integer boardId;
    Long writerId;
    String title;
    String content;

    public static PostSaveRequest from(PostEntity entity){
        return new PostSaveRequest(entity.getId(), entity.getBoardId(), entity.getUserId(), entity.getTitle(), entity.getContent());
    }
}
