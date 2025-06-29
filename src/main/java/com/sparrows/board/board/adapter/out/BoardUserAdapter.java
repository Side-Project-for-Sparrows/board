package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.adapter.repository.BoardUserRepository;
import com.sparrows.board.board.model.entity.BoardUserEntity;
import com.sparrows.board.board.port.out.BoardUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public BoardUserEntity findById(Long userId) {
        return boardUserRepository.findById(userId).orElseThrow();
    }

    @Override
    public boolean existsByUserIdAndSchoolId(Long userId, Integer schoolId) {
        return boardUserRepository.existsByIdAndSchoolId(userId, schoolId);
    }
}
