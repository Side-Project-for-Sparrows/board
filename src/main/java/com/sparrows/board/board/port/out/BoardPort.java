package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.BoardEntity;

import java.util.List;
import java.util.Optional;

public interface BoardPort {

    List<BoardEntity> getBoardsBySchoolId(Integer schoolId);

    List<BoardEntity> getBoardsBySchoolId(Integer schoolId, Boolean isPublic);

    BoardEntity save(BoardEntity boardEntity);

    Optional<BoardEntity> findById(int id);

    List<BoardEntity> getAllBoardsByUserId(long id);

    BoardEntity updateBoardById(BoardEntity boardEntity);
    void deleteById(int id);

    long count();

    void saveAll(List<BoardEntity> boards);

    boolean existsByNameAndSchoolId(String name, Integer schoolId);

    BoardEntity findByNameAndSchoolId(String 테스트_게시판, Integer schoolId);
}
