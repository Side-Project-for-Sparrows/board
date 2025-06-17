package com.sparrows.board.board.port.in;

import com.sparrows.board.board.model.dto.client.*;
import com.sparrows.board.board.model.entity.BoardEntity;

import java.util.List;

public interface BoardUsecase {
    BoardCreateResponseDto createNewBoard(Long userId, BoardEntity boardEntity);
    //유저와 연관된 모든 게시판 가져오기
    List<BoardEntity> searchAllBoardsByUserId(long userId);
    BoardEntity update(BoardEntity boardEntity);
    void delete(int id);
    List<BoardEntity> searchBoardByQuery(String query);
    BoardUpdateResponseDto validateAndUpdateBoard(Long userId, BoardEntity convert);
    BoardDeleteResponseDto deleteBoard(Long userId, Integer boardId);
    BoardWithdrawResponseDto withdrawBoard(Integer boardId, Long userId, Long transferToUserId);
    BoardJoinResponseDto join(Long userId, BoardEntity board);
}
