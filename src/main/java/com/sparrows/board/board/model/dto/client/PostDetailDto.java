package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailDto {
    Long postId;
    Integer boardId;
    Long writerId;
    String nickname;
    String title;
    String content;
    Integer likeCount;
    Integer commentCount;
    Integer viewCount;

    List<CommentDetailDto> commentDetailDtos;
    List<PostImageDetailDto> postImageDetailDtos;
    LocalDateTime createdAt;
}
