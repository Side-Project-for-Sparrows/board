package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentPort {
    boolean save(CommentEntity commentEntity);
    List<CommentEntity> searchByPostId(Long postId);
    Optional<CommentEntity> findById(Long commentId);
}
