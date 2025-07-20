package com.sparrows.board.board.controller;

import com.sparrows.board.board.model.dto.client.*;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.port.in.BoardUsecase;
import com.sparrows.board.board.port.in.PostUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardUsecase boardUsecase;

    @Autowired
    PostUsecase postUsecase;

    @PostMapping
    public ResponseEntity<BoardCreateResponseDto> createPrivateBoard(@RequestBody BoardCreateRequestDto dto){
        return ResponseEntity.ok(boardUsecase.createNewBoard(dto.getUserId(), dto.convert()));
    }

    @PutMapping
    public ResponseEntity<BoardUpdateResponseDto> updatePrivateBoard(@RequestBody BoardUpdateRequestDto dto){
        return ResponseEntity.ok(boardUsecase.validateAndUpdateBoard(dto.getUserId(), dto.convert()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BoardWithdrawResponseDto> withDrawBoard(@RequestHeader("X-Requester-Id") Long userId, @RequestBody BoardWithdrawRequestDto dto){
        return ResponseEntity.ok(boardUsecase.withdrawBoard(dto.getBoardId(), userId, dto.getTransferToUserId()));
    }

    @PostMapping("/join")
    public ResponseEntity<BoardJoinResponseDto> join(@RequestBody BoardJoinRequestDto dto){
        return ResponseEntity.ok(boardUsecase.join(dto.getUserId(), dto.to()));
    }


    // 1. 사용자 ID로 게시판 목록 조회
    @GetMapping("/search/user")
    public ResponseEntity<List<BoardSearchResponseDto>> getBoardsByUserId(@RequestHeader("X-Requester-Id") Long userId) {
        List<BoardSearchResponseDto> boardEntities = boardUsecase.searchAllBoardsByUserId(userId);
        return ResponseEntity.ok(boardEntities);
    }

    // 2. 검색어(query)로 게시판 목록 조회 (Elasticsearch 기반)
    @GetMapping("/search")
    public ResponseEntity<List<BoardSearchResponseDto>> searchBoards(@RequestHeader("X-Requester-Id") Long userId, @RequestParam String query) {
        List<BoardSearchResponseDto> boardEntities = boardUsecase.searchBoardByQuery(userId, query);

        return ResponseEntity.ok(boardEntities);
    }

    @GetMapping("/{boardId}/posts")
    public ResponseEntity<List<PostSummaryResponseDto>> getPostsByBoard(@PathVariable Integer boardId) {
        List<PostEntity> posts = postUsecase.getPostsByBoardId(boardId);

        List<PostSummaryResponseDto> result = posts.stream()
                .map(post -> PostSummaryResponseDto.from(post, "익명")) // 추후 nickname 로직 연결
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/member/{boardId}")
    public ResponseEntity<BoardMemberResponseDto> getMembers(@RequestHeader("X-Requester-Id") Long requesterId, @PathVariable Integer boardId) {
        return ResponseEntity.ok(boardUsecase.getMembers(requesterId, boardId));
    }
}
