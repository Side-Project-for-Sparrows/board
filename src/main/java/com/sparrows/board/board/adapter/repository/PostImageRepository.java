package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImageEntity, Long> {
}
