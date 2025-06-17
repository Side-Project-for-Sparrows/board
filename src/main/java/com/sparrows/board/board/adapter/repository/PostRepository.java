package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByIdIn(List<Long> postIds);

    List<PostEntity> findByBoardId(Long boardId);

    List<PostEntity> findByUserId(Long userId);

    Page<PostEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
