package com.sparrows.board.minio.service;

import com.sparrows.board.minio.config.MinioProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageService {
    private final S3Client s3Client;
    private final MinioProperties properties;
    private boolean isConnected;

    @PostConstruct
    public void init(){
        try{
            initBucket();
        }catch (Exception e){
            log.error("Minio 서비스 초기화 실패");
        }
    }

    public void initBucket(){
        if(isConnected) return;

        String bucket = properties.getBucket();
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucket).build());
            isConnected = true;
        } catch (NoSuchBucketException e) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
            isConnected = true;
        } catch (Exception e){
            log.error("Minio 서비스 초기화 실패");
            throw new RuntimeException("Minio 서비스 초기화 실패");
        }
    }

    public String upload(MultipartFile file, String key) throws IOException {
        initBucket();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build()).toString();
    }

    public byte[] download(String key) throws IOException {
        initBucket();

        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();

        try (InputStream inputStream = s3Client.getObject(getRequest);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
    }
}
