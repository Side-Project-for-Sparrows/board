package com.sparrows.board.board.adapter.in;

import com.sparrows.board.board.model.dto.client.CommentDetailDto;
import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.port.in.CommentUsecase;
import com.sparrows.board.board.port.out.CommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentUsecaseImpl implements CommentUsecase {

    private final CommentPort commentPort;
    @Override
    public boolean saveComment(CommentEntity commentEntity) {
        return commentPort.save(commentEntity);
    }

    @Override
    public Page<CommentDetailDto> getCommentsByPostId(Long postId, int page, int size) {
        return commentPort.getCommentsByPostId(postId, page, size);
    }
}
