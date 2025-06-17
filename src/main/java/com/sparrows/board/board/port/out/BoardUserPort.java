package com.sparrows.board.board.port.out;

public interface BoardUserPort {
    void save(Long userId, Integer schoolId);

    boolean existsByUserIdAndSchoolId(Long userId, Integer schoolId);
}
