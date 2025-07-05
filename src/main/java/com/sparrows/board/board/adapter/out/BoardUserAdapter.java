package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.adapter.repository.BoardUserRepository;
import com.sparrows.board.board.model.entity.BoardUserEntity;
import com.sparrows.board.board.port.out.BoardUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BoardUserAdapter implements BoardUserPort {
    @Autowired
    BoardUserRepository boardUserRepository;

    @Override
    public void save(Long userId, String nickname, Integer schoolId) {
        BoardUserEntity entity = new BoardUserEntity();
        entity.setId(userId);
        entity.setNickname(nickname);
        entity.setSchoolId(schoolId);
        boardUserRepository.save(entity);
    }

    @Override
    public Optional<BoardUserEntity> findById(Long userId) {
        return boardUserRepository.findById(userId);
    }

    @Override
    public boolean existsByUserIdAndSchoolId(Long userId, Integer schoolId) {
        return boardUserRepository.existsByIdAndSchoolId(userId, schoolId);
    }
}
