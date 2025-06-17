package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.UserBoardRelationEntity;

public interface UserBoardRelationPort {
    boolean isUserInBoard(Long userId, Integer boardId);

    int countUsersByBoardId(Integer boardId);

    UserBoardRelationEntity save(UserBoardRelationEntity userBoardRelationEntity);

    UserBoardRelationEntity findByUserIdAndBoardId(Long userId, Integer boardId);

    void deleteByBoardId(Integer boardId);

    void deleteByBoardIdAndUserId(Integer boardId, Long userId);
}
