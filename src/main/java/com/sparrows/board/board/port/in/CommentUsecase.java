package com.sparrows.board.board.port.in;

import com.sparrows.board.board.model.dto.client.CommentDetailDto;
import com.sparrows.board.board.model.entity.CommentEntity;
import org.springframework.data.domain.Page;

public interface CommentUsecase {
    boolean saveComment(CommentEntity entity);

    Page<CommentDetailDto> getCommentsByPostId(Long postId, int page, int size);
}
