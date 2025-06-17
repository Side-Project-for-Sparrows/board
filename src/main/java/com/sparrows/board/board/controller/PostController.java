package com.sparrows.board.board.controller;

import com.sparrows.board.board.model.dto.client.PostCreateRequestDto;
import com.sparrows.board.board.model.dto.client.PostCreateResponseDto;
import com.sparrows.board.board.model.dto.client.PostDetailDto;
import com.sparrows.board.board.model.dto.client.PostSearchResponseDto;
import com.sparrows.board.board.model.dto.internal.PostSearchRequest;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.model.entity.PostImageEntity;
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
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostUsecase postUsecase;

    // 0. 게시글 들어가기
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable("postId") Long postId) {
        //요청 유저 검증 로직 필요

        PostDetailDto post = postUsecase.getPostDetail(postId);
        return ResponseEntity.ok(post);
    }

    // 1. 사용자 ID로 게시글 목록 조회
    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List<PostSearchResponseDto>> getPostsByUserId(@PathVariable("userId") Long userId) {
        //요청 유저 검증 로직 필요

        List<PostEntity> posts = postUsecase.getAllPostsByUserId(userId);

        List<PostSearchResponseDto> responses = new ArrayList<>();
        for(PostEntity entity: posts){
            responses.add(PostSearchResponseDto.from(entity));
        }
        return ResponseEntity.ok(responses);
    }

    // 2. 검색어(query)로 게시글 목록 조회 (Elasticsearch 기반)
    @GetMapping("/query")
    public ResponseEntity<List<PostSearchResponseDto>> searchPostByQuery(@RequestBody PostSearchRequest request) {
        //요청 유저 속한 게시판 단위로!!! 검증 로직 필요

        List<PostEntity> posts = postUsecase.getPostsByQuery(request.getBoardId(), request.getQuery());

        List<PostSearchResponseDto> postResponses = new ArrayList<>();
        for(PostEntity entity: posts){
            postResponses.add(PostSearchResponseDto.from(entity));
        }
        return ResponseEntity.ok(postResponses);
    }

    // 3. 게시글 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostCreateResponseDto> createBoard(
            @RequestPart("post") PostCreateRequestDto postCreateRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile[] images
    ) throws Exception {
        boolean result = postUsecase.savePost(postCreateRequestDto.to(), images);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostCreateResponseDto(result));
    }

    // 3. 최근 게시글 조회
    @GetMapping("/search")
    public ResponseEntity<List<PostSearchResponseDto>> getRecentPosts(Pageable pageable) throws Exception {
        Page<PostEntity> posts =  postUsecase.getPosts(pageable);

        List<PostSearchResponseDto> postResponses = new ArrayList<>();
        for(PostEntity entity: posts){
            postResponses.add(PostSearchResponseDto.from(entity));
        }
        return ResponseEntity.status(HttpStatus.OK).body(postResponses);
    }

    @GetMapping("/image/{postId}/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable("postId") String postId, @PathVariable("fileName") String fileName) {
        try {
            // 실제 저장 경로 설정
            Path path = Paths.get(PostImageEntity.baseUrl, postId, fileName);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
