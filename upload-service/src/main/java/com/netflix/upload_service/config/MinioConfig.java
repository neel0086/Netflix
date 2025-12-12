package com.netflix.upload_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.http.apache.ApacheHttpClient;


import java.net.URI;


@Configuration
public class MinioConfig {
    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.accesskey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.regoin:us-east-1}")
    private String region;

    @Bean
    public S3Client s3Client(){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .endpointOverride(URI.create(minioEndpoint))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(Region.of(region))
                .httpClientBuilder(ApacheHttpClient.builder())
                .build();

    }
}
