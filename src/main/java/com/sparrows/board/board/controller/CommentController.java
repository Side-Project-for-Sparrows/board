package com.sparrows.board.board.controller;

import com.sparrows.board.board.model.dto.client.CommentCreateRequestDto;
import com.sparrows.board.board.model.dto.client.CommentCreateResponseDto;
import com.sparrows.board.board.model.dto.client.CommentDetailDto;
import com.sparrows.board.board.port.in.CommentUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/post/comment")
public class CommentController {
    @Autowired
    CommentUsecase commentUsecase;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentCreateResponseDto> createComment(
            @RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        boolean result = commentUsecase.saveComment(commentCreateRequestDto.to());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentCreateResponseDto(result));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Page<CommentDetailDto>> getCommentsByPostId(
            @RequestHeader("X-Requester-Id") Long requesterId,
            @PathVariable(name="postId") Long postId,
            @RequestParam(name="page") Integer page
            ) {
        Page<CommentDetailDto> commentPage = commentUsecase.getCommentsByPostId(postId, page,10);
        return ResponseEntity.ok(commentPage);
    }
}