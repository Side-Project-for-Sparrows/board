package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.adapter.repository.UserBoardRelationRepository;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import com.sparrows.board.board.port.out.UserBoardRelationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBoardRelationAdapter implements UserBoardRelationPort {

    private final UserBoardRelationRepository userBoardRelationRepository;

    @Override
    public boolean isUserInBoard(Long userId, Integer boardId) {
        return userBoardRelationRepository.existsByUserIdAndBoardId(userId, boardId);
    }

    @Override
    public int countUsersByBoardId(Integer boardId) {
        return userBoardRelationRepository.countByBoardId(boardId);
    }

    @Override
    public UserBoardRelationEntity save(UserBoardRelationEntity userBoardRelationEntity) {
        return userBoardRelationRepository.save(userBoardRelationEntity);
    }

    @Override
    public UserBoardRelationEntity findByUserIdAndBoardId(Long userId, Integer boardId) {
        return userBoardRelationRepository.findByUserIdAndBoardId(userId, boardId);
    }

    @Override
    public void deleteByBoardId(Integer boardId){
        userBoardRelationRepository.deleteByBoardId(boardId);
    }

    @Override
    public void deleteByBoardIdAndUserId(Integer boardId, Long userId) {
        userBoardRelationRepository.deleteByBoardIdAndUserId(boardId,userId);
    }

    @Override
    public List<BoardEntity> findByUserId(long userId) {
        return userBoardRelationRepository.findBoardsByUserId(userId);
    }

    @Override
    public List<UserBoardRelationEntity> findUsersByBoardId(Integer boardId) {
        return userBoardRelationRepository.findByBoardId(boardId);
    }
}
