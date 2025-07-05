package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;

import java.util.List;

public interface UserBoardRelationPort {
    boolean isUserInBoard(Long userId, Integer boardId);

    int countUsersByBoardId(Integer boardId);

    UserBoardRelationEntity save(UserBoardRelationEntity userBoardRelationEntity);

    UserBoardRelationEntity findByUserIdAndBoardId(Long userId, Integer boardId);

    void deleteByBoardId(Integer boardId);

    void deleteByBoardIdAndUserId(Integer boardId, Long userId);

    List<BoardEntity> findByUserId(long userId);
}
