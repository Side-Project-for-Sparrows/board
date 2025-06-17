package com.sparrows.board.board.adapter.in;

import com.sparrows.board.board.model.entity.BoardAuthority;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import com.sparrows.board.board.port.in.UserBoardUsecase;
import com.sparrows.board.board.port.out.BoardPort;
import com.sparrows.board.board.port.out.UserBoardRelationPort;
import com.sparrows.board.board.port.out.BoardUserPort;
import com.sparrows.board.kafka.payload.user.UserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserBoardUsecaseImpl implements UserBoardUsecase {

    @Autowired
    BoardUserPort boardUserPort;

    @Autowired
    BoardPort boardPort;

    @Autowired
    UserBoardRelationPort userBoardRelationPort;

    @Override
    @Transactional
    public void handleUserCreated(Long userId, Integer schoolId, UserType userType) {
        boardUserPort.save(userId, schoolId);
        addInitializeUserBoardRelation(userId, schoolId, userType);
    }

    @Transactional
    protected void addInitializeUserBoardRelation(Long userId, Integer schoolId, UserType userType) {
        if (userType == UserType.ADMIN) return;

        registerUserToBoards(userId, schoolId, boardPort.getBoardsBySchoolId(0));
        if (userType == UserType.OFFICIAL) {
            registerUserToBoards(userId, schoolId, boardPort.getBoardsBySchoolId(schoolId));
        }
    }

    private void registerUserToBoards(Long userId, Integer schoolId, List<BoardEntity> boards) {
        for (BoardEntity board : boards) {
            UserBoardRelationEntity userBoard = generateUserBoardEntity(userId, board);
            userBoardRelationPort.save(userBoard);
        }
    }

    private UserBoardRelationEntity generateUserBoardEntity(Long userId, BoardEntity board) {
        return UserBoardRelationEntity.builder()
                .userId(userId)
                .board(board)
                .BoardAuthority(BoardAuthority.CITIZEN)
                .build();
    }
}
