package com.sparrows.board.board.controller;

import com.sparrows.board.board.model.dto.client.*;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.port.in.BoardUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardUsecase boardUsecase;

    @PostMapping
    public ResponseEntity<BoardCreateResponseDto> createPrivateBoard(@RequestBody BoardCreateRequestDto dto){
        return ResponseEntity.ok(boardUsecase.createNewBoard(dto.getUserId(), dto.convert()));
    }

    @PutMapping
    public ResponseEntity<BoardUpdateResponseDto> updatePrivateBoard(@RequestBody BoardUpdateRequestDto dto){
        return ResponseEntity.ok(boardUsecase.validateAndUpdateBoard(dto.getUserId(), dto.convert()));
    }

    @DeleteMapping
    public ResponseEntity<BoardDeleteResponseDto> deletePrivateBoard(@RequestBody BoardDeleteRequestDto dto){
        return ResponseEntity.ok(boardUsecase.deleteBoard(dto.getUserId(), dto.getBoardId()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BoardWithdrawResponseDto> withDrawBoard(@RequestBody BoardWithdrawRequestDto dto){
        return ResponseEntity.ok(boardUsecase.withdrawBoard(dto.getBoardId(), dto.getUserId(),dto.getTransferToUserId()));
    }

    @PostMapping("/join")
    public ResponseEntity<BoardJoinResponseDto> join(@RequestBody BoardJoinRequestDto dto){
        return ResponseEntity.ok(boardUsecase.join(dto.getUserId(), dto.to()));
    }


    // 1. 사용자 ID로 게시판 목록 조회
    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List<BoardSearchResponseDto>> getBoardsByUserId(@PathVariable Long userId) {
        List<BoardEntity> boardEntities = boardUsecase.searchAllBoardsByUserId(userId);

        List<BoardSearchResponseDto> boardSearchResponseDtos = new ArrayList<>();
        for(BoardEntity entity: boardEntities){
            boardSearchResponseDtos.add(BoardSearchResponseDto.from(entity));
        }
        return ResponseEntity.ok(boardSearchResponseDtos);
    }

    // 2. 검색어(query)로 게시판 목록 조회 (Elasticsearch 기반)
    @GetMapping("/search")
    public ResponseEntity<List<BoardSearchResponseDto>> searchBoards(@RequestParam String query) {
        List<BoardEntity> boardEntities = boardUsecase.searchBoardByQuery(query);

        List<BoardSearchResponseDto> boardSearchResponseDtos = new ArrayList<>();
        for(BoardEntity entity: boardEntities){
            boardSearchResponseDtos.add(BoardSearchResponseDto.from(entity));
        }
        return ResponseEntity.ok(boardSearchResponseDtos);
    }
}
