package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.dto.internal.BoardSaveRequest;

public interface BoardEventPort {
    void publishBoardCreateEvent(BoardSaveRequest request);
}
