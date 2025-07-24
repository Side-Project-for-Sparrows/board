package com.sparrows.board.board.factory;

import com.sparrows.board.board.model.dto.client.PostCreateRequestDto;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.port.out.BoardPort;
import com.sparrows.board.exception.handling.BoardNotFouncException;
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
    private final BoardPort boardPort;

    public PostDetailDto buildPostDetailDto(PostEntity post){
        BoardUserEntity user = boardUserPort.findById(post.getUser().getId()).orElseThrow(() -> new BoardUserNotFoundException());

        return PostDetailDto.builder()
                .postId(post.getId())
                .boardId(post.getBoard().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .postImageDetailDtos(PostImageDetailDto.from(post.getPostImageEntities()))
                .createdAt(post.getCreatedAt())
                .writerId(post.getUser().getId())
                .nickname(user.getNickname())
                .build();
    }

    public PostEntity buildPost(PostCreateRequestDto requestDto) {
        BoardEntity board = boardPort.findById(requestDto.getBoardId()).orElseThrow(BoardNotFouncException::new);
        BoardUserEntity user = boardUserPort.findById(requestDto.getUserId()).orElseThrow(BoardUserNotFoundException::new);

        return PostEntity.builder()
                .user(user)
                .board(board)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
    }
}
