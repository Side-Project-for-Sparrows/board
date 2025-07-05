package com.sparrows.board.board.controller;

import com.sparrows.board.board.model.dto.client.CommentCreateResponseDto;
import com.sparrows.board.board.model.dto.client.LikeResponseDto;
import com.sparrows.board.board.port.in.LikeUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post/like")
public class LikeController {
    private final LikeUsecase likeUsecase;

    @PostMapping("/{postId}")
    public ResponseEntity<LikeResponseDto> likePost(@RequestHeader("X-Requester-Id") Long requesterId, @PathVariable("postId") Long postId) {
        boolean result = likeUsecase.likePost(requesterId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LikeResponseDto(result));
    }

    @PostMapping("/{postId}/{commentId}")
    public ResponseEntity<LikeResponseDto> likeComment(@RequestHeader("X-Requester-Id") Long requesterId, @PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId) {
        boolean result = likeUsecase.likeComment(requesterId,postId,commentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LikeResponseDto(result));
    }
}
