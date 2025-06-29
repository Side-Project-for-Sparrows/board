package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.BoardUserEntity;

public interface BoardUserPort {
    void save(Long userId, String nickname, Integer schoolId);
    BoardUserEntity findById(Long userId);
    boolean existsByUserIdAndSchoolId(Long userId, Integer schoolId);
}
