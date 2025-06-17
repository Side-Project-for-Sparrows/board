package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.feignclient.BoardIndexClient;
import com.sparrows.board.board.model.dto.internal.PostSearchRequest;
import com.sparrows.board.board.model.dto.internal.PostSearchResponse;
import com.sparrows.board.board.port.out.PostSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostSearchAdapter implements PostSearchPort {
    private final BoardIndexClient boardIndexClient;

    @Override
    public PostSearchResponse search(PostSearchRequest request) {
        return boardIndexClient.searchPost(request);
    }
}
