package com.sparrows.board.board.factory;

import com.sparrows.board.board.model.dto.client.CommentDetailDto;
import com.sparrows.board.board.model.entity.BoardUserEntity;
import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.port.out.BoardUserPort;
import com.sparrows.board.exception.handling.BoardUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CommentFactory {
    private final BoardUserPort boardUserPort;

    public List<CommentDetailDto> buildCommentDetailDtos(List<CommentEntity> comments) {
        List<CommentDetailDto> dtos = new ArrayList<>();

        for(CommentEntity comment: comments){
            BoardUserEntity user = boardUserPort.findById(comment.getUserId()).orElseThrow(BoardUserNotFoundException::new);

            CommentDetailDto dto = CommentDetailDto.builder()
                    .id(comment.getId())
                    .userId(user.getId())
                    .nickname(user.getNickname())
                    .content(comment.getContent())
                    .likeCount(comment.getLikeCount())
                    .build();
            dtos.add(dto);
        }

        return dtos;
    }

    public Page<CommentDetailDto> buildCommentDetailDtos(Page<CommentEntity> comments) {
        List<CommentDetailDto> dtos = new ArrayList<>();

        return comments.map(entity -> {
            BoardUserEntity user = boardUserPort.findById(entity.getUserId()).orElseThrow(BoardUserNotFoundException::new);

            return CommentDetailDto.builder()
                    .id(entity.getId())
                    .userId(user.getId())
                    .nickname(user.getNickname())
                    .content(entity.getContent())
                    .likeCount(entity.getLikeCount())
                    .build();
        });
    }
}
