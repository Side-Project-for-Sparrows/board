package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.model.entity.PostImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagePort {
    PostImageEntity save(PostEntity post, MultipartFile[] multipartFiles) throws IOException;
}
