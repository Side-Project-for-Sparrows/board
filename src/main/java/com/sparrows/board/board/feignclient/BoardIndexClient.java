package com.sparrows.board.board.feignclient;

import com.sparrows.board.board.model.dto.internal.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "board-index-service", url = "${search.service.url}")
public interface BoardIndexClient {
    @PostMapping("/index/board")
    BoardSearchResponse searchBoard(BoardSearchRequest request);

    @PostMapping("/index/post")
    PostSearchResponse searchPost(PostSearchRequest request);

    @PostMapping("/index/save")
    BoardSaveResponse saveBoard(BoardSaveRequest request);

    @PostMapping("/index/save")
    PostSaveResponse savePost(PostSaveRequest request);
}
