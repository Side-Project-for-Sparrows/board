package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity b SET b.name = :name, b.description = :description WHERE b.id = :id")
    BoardEntity updateBoardById(BoardEntity boardEntity);

    List<BoardEntity> findBySchoolId(int schoolId);

    List<BoardEntity> findBySchoolIdAndIsPublic(int schoolId, boolean isPublic);

    boolean existsByNameAndSchoolId(String name, Integer schoolId);

    BoardEntity findByNameAndSchoolId(String name, Integer schoolId);
}
