package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.LikeEntity;

public interface LikePort {
    LikeEntity findByPostIdAndUserId(Long postId, Long userId);

    void save(LikeEntity like);

    void delete(LikeEntity entity);
}
