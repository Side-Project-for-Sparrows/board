package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.dto.client.CommentDetailDto;
import com.sparrows.board.board.model.entity.CommentEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CommentPort {
    boolean save(CommentEntity commentEntity);
    List<CommentEntity> searchByPostId(Long postId);
    Optional<CommentEntity> findById(Long commentId);

    Page<CommentDetailDto> getCommentsByPostId(Long postId, int page, int size);
}
