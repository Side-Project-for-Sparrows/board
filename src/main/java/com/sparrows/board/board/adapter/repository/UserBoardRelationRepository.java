package com.sparrows.board.board.adapter.repository;

import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBoardRelationRepository extends JpaRepository<UserBoardRelationEntity, Integer> {
    @Query("SELECT ub.board FROM UserBoardRelationEntity ub WHERE ub.userId = :userId")
    List<BoardEntity> findBoardsByUserId(@Param("userId") Long userId);

    UserBoardRelationEntity findByUserIdAndBoardId(Long userId, Integer boardId);

    boolean existsByUserIdAndBoardId(Long userId, Integer boardId);

    int countByBoardId(Integer boardId);

    void deleteByBoardId(Integer boardId);

    void deleteByBoardIdAndUserId(Integer boardId,Long userId);

    List<UserBoardRelationEntity> findByUserId(long userId);
}