package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.LikeEntity;

import java.util.Optional;

public interface LikePort {
    Optional<LikeEntity> findByCommentIdAndUserId(Long commentId, Long userId);

    Optional<LikeEntity> findByPostIdAndUserId(Long postId, Long userId);

    void save(LikeEntity like);

    void delete(LikeEntity entity);
}
