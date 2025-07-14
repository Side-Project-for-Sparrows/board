package com.sparrows.board.board.factory;

import com.sparrows.board.exception.handling.BoardUserNotFoundException;
import com.sparrows.board.board.model.dto.client.PostDetailDto;
import com.sparrows.board.board.model.dto.client.PostImageDetailDto;
import com.sparrows.board.board.model.entity.BoardUserEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.port.out.BoardUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostFactory {
    private final BoardUserPort boardUserPort;

    public PostDetailDto buildPostDetailDto(PostEntity post){
        BoardUserEntity user = boardUserPort.findById(post.getUserId()).orElseThrow(() -> new BoardUserNotFoundException());

        return PostDetailDto.builder()
                .postId(post.getId())
                .boardId(post.getBoardId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .postImageDetailDtos(PostImageDetailDto.from(post.getPostImageEntities()))
                .createdAt(post.getCreatedAt())
                .writerId(post.getUserId())
                .nickname(user.getNickname())
                .build();
    }
}
