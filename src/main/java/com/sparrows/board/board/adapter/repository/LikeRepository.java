package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByPostIdAndUserId(Long postId, Long userId);
    Optional<LikeEntity> findByCommentIdAndUserId(Long commentId, Long userId);
}
