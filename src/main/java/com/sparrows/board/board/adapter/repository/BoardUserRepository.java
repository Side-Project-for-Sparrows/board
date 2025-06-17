package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.BoardUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUserEntity,Long> {
    boolean existsByIdAndSchoolId(Long userId, Integer schoolId);
}
