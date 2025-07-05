package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.BoardUserEntity;

import java.util.Optional;

public interface BoardUserPort {
    void save(Long userId, String nickname, Integer schoolId);
    Optional<BoardUserEntity> findById(Long userId);
    boolean existsByUserIdAndSchoolId(Long userId, Integer schoolId);
}
