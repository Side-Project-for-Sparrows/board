package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSummaryResponseDto {
    private Long postId;
    private String title;
    private String nickname;
    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;
    private LocalDateTime createdAt;

    public static PostSummaryResponseDto from(PostEntity entity, String nickname) {
        return PostSummaryResponseDto.builder()
                .postId(entity.getId())
                .title(entity.getTitle())
                .nickname(nickname) // UserService 통해 얻거나, entity.getUser().getNickname() 등
                .likeCount(entity.getLikeCount())
                .commentCount(entity.getCommentCount())
                .viewCount(entity.getViewCount())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

