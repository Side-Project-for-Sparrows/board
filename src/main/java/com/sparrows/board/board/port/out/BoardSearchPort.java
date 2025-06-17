package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.dto.internal.BoardSearchRequest;
import com.sparrows.board.board.model.dto.internal.BoardSearchResponse;

public interface BoardSearchPort {
    BoardSearchResponse search(BoardSearchRequest request);
}
