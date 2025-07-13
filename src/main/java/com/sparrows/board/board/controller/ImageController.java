package com.sparrows.board.board.controller;

import com.sparrows.board.minio.service.MinioStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post/image")
public class ImageController {
    private final MinioStorageService storageService;

    //이미지 조회
    @GetMapping("/{key}")
    public ResponseEntity<Resource> getImage(@PathVariable("key") String key) {
        try {
            byte[] data = storageService.download(key);  // ← MinIO에서 바이트로 읽어오기
            String contentType = Files.probeContentType(Path.of(key)); // 파일 이름에서 추측 (대안도 있음)

            ByteArrayResource resource = new ByteArrayResource(data);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                    .body(resource);
        } catch (Exception e) {
            log.error("이미지 다운로드 실패: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String url = storageService.upload(file, "uploads/" + file.getOriginalFilename());
        return ResponseEntity.ok(url);
    }
}