package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PostDetailDto {
    Long postId;
    Integer boardId;
    Long writerId;
    String nickname;
    String title;
    String content;
    Integer likes;
    Integer views;
    List<CommentDetailDto> commentDetailDtos;
    List<LikeDetailDto> likeDetailDtos;
    List<PostImageDetailDto> postImageDetailDtos;
    LocalDateTime createdAt;

    public PostDetailDto (PostEntity entity, String nickname){
        this.postId = entity.getId();
        this.boardId = entity.getBoardId();
        this.writerId = entity.getUserId();
        this.nickname = nickname;
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.commentDetailDtos = CommentDetailDto.from(entity.getComments());
        this.likeDetailDtos = LikeDetailDto.from(entity.getLikes());
        this.postImageDetailDtos = PostImageDetailDto.from(entity.getPostImageEntities());
        this.likes = entity.getLikeCount();
        this.views = entity.getViewCount();
        this.createdAt = entity.getCreatedAt();
    }
}
