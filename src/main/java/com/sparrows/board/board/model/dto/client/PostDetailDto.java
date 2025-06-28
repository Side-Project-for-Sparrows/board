package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostDetailDto {
    Long postId;
    Integer boardId;
    Long writer;
    String title;
    String content;
    Integer likes;
    Integer views;
    List<CommentDetailDto> commentDetailDtos;
    List<LikeDetailDto> likeDetailDtos;
    List<PostImageDetailDto> postImageDetailDtos;

    public PostDetailDto (PostEntity entity){
        this.postId = entity.getId();
        this.boardId = entity.getBoardId();
        this.writer = entity.getUserId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.commentDetailDtos = CommentDetailDto.from(entity.getComments());
        this.likeDetailDtos = LikeDetailDto.from(entity.getLikes());
        this.postImageDetailDtos = PostImageDetailDto.from(entity.getPostImageEntities());
        this.likes = entity.getLikeCount();
        this.views = entity.getViewCount();
    }
}
