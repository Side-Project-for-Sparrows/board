package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.adapter.repository.PostImageRepository;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.model.entity.PostImageEntity;
import com.sparrows.board.board.port.out.ImagePort;
import com.sparrows.board.minio.service.MinioStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class MinioImageAdapter implements ImagePort {
    private final PostImageRepository postImageRepository;
    private final MinioStorageService minioStorageService;

    @Override
    public PostImageEntity save(PostEntity post, MultipartFile[] multipartFiles) throws IOException{
        int order = 0;
        for(MultipartFile file: multipartFiles){
            String key = PostImageEntity.createKey(post,order++);
            minioStorageService.upload(file,key);
            postImageRepository.save(new PostImageEntity(post,key));
        }
        return null;
    }
}
