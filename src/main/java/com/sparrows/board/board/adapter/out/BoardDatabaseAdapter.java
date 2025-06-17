package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.adapter.repository.BoardRepository;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.port.out.BoardPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BoardDatabaseAdapter implements BoardPort {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public List<BoardEntity> getBoardsBySchoolId(Integer schoolId) {
        return boardRepository.findBySchoolId(schoolId);
    }

    @Override
    public List<BoardEntity> getBoardsBySchoolId(Integer schoolId, Boolean isPublic) {
        return boardRepository.findBySchoolIdAndIsPublic(schoolId,isPublic);
    }

    @Override
    public BoardEntity save(BoardEntity boardEntity) {
        return boardRepository.save(boardEntity);
    }

    @Override
    public Optional<BoardEntity> findById(int boardId) {
        return boardRepository.findById(boardId);
    }

    @Override
    public List<BoardEntity> getAllBoardsByUserId(long userId) {
        return null;
    }

    @Override
    public BoardEntity updateBoardById(BoardEntity boardEntity) {
        return boardRepository.updateBoardById(boardEntity);
    }

    @Override
    public void deleteById(int id) {
        boardRepository.deleteById(id);
    }

    @Override
    public long count() {
        return boardRepository.count();
    }

    @Override
    public void saveAll(List<BoardEntity> boards) {
        boardRepository.saveAll(boards);
    }

    @Override
    public boolean existsByNameAndSchoolId(String name, Integer schoolId) {
        return boardRepository.existsByNameAndSchoolId(name,schoolId);
    }

    @Override
    public BoardEntity findByNameAndSchoolId(String name, Integer schoolId) {
        return boardRepository.findByNameAndSchoolId(name,schoolId);
    }
}
