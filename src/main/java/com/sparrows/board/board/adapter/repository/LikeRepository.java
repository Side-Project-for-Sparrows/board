package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    LikeEntity findByPostIdAndUserId(Long postId, Long userId);
}
