package com.sparrows.board.minio.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

    private final MinioProperties properties;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())))
                .endpointOverride(URI.create(properties.getEndpoint())) // MinIO는 custom endpoint
                .region(Region.US_EAST_1) // 아무거나 가능, MinIO는 무시함\
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // 핵심 설정
                        .build())
                .build();
    }
}
