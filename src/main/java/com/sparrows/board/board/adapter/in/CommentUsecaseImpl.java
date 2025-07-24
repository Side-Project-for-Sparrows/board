package com.sparrows.board.board.adapter.in;

import com.sparrows.board.board.model.dto.client.CommentDetailDto;
import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.port.in.CommentUsecase;
import com.sparrows.board.board.port.out.CommentPort;
import com.sparrows.board.board.port.out.PostPort;
import com.sparrows.board.exception.handling.PostNotFouncException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CommentUsecaseImpl implements CommentUsecase {

    private final CommentPort commentPort;
    private final PostPort postPort;

    @Override
    @Transactional
    public boolean saveComment(CommentEntity commentEntity) {
        PostEntity post = postPort.findById(commentEntity.getPost().getId()).orElseThrow(PostNotFouncException::new);
        post.increaseComment();
        return commentPort.save(commentEntity);
    }

    @Override
    public Page<CommentDetailDto> getCommentsByPostId(Long postId, int page, int size) {
        return commentPort.getCommentsByPostId(postId, page, size);
    }
}
