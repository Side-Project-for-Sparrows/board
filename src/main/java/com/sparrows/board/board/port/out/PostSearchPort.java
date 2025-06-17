package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.dto.internal.PostSearchRequest;
import com.sparrows.board.board.model.dto.internal.PostSearchResponse;

public interface PostSearchPort {
    PostSearchResponse search(PostSearchRequest request);
}
