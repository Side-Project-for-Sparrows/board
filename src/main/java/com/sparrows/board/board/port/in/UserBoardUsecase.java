package com.sparrows.board.board.port.in;


import com.sparrows.board.kafka.payload.user.UserType;

public interface UserBoardUsecase {
    void handleUserCreated(Long userId, Integer schoolId, UserType userType);
}
