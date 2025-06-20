package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.model.entity.PostImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSearchResponseDto {
    Long userId;
    Integer boardId;
    String title;
    String content;
    List<String> imageUrls;
    Integer likes;
    Integer viewCounts;

    public static PostSearchResponseDto from(PostEntity post){
        return PostSearchResponseDto.builder()
                .userId(post.getUserId())
                .boardId(post.getBoardId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrls(post.getPostImageEntities().stream()
                        .map(PostImageEntity::getUrl)
                        .collect(Collectors.toList()))
                .likes(post.getLikeCount())
                .viewCounts(post.getViewCount())
                .build();
    }
}
