package com.sparrows.board.board.controller;

import com.sparrows.board.board.model.dto.client.*;
import com.sparrows.board.board.model.dto.internal.PostSearchRequest;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.model.entity.PostImageEntity;
import com.sparrows.board.board.port.in.CommentUsecase;
import com.sparrows.board.board.port.in.PostUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post/comment")
public class CommentController {
    @Autowired
    CommentUsecase commentUsecase;

    // 댓글 생성
    @PostMapping("/comment")
    public ResponseEntity<CommentCreateResponseDto> createComment(
            @RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        boolean result = commentUsecase.saveComment(commentCreateRequestDto.to());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentCreateResponseDto(result));
    }
}