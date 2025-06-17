package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
