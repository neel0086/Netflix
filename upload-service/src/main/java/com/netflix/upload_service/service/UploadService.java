package com.netflix.upload_service.service;

import com.netflix.upload_service.dto.UploadResponse;
import com.netflix.upload_service.modal.VideoDocument;
import com.netflix.upload_service.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.Instant;
import java.util.UUID;

@Service
public class UploadService {
    private final S3Client s3Client;
    private final VideoRepository videoRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String bucket;
    private final String uploadTopic;

    public UploadService(S3Client s3Client,
                         VideoRepository videoRepository,
                         KafkaTemplate<String, String> kafkaTemplate,
                         @Value("${minio.bucket.uploads}") String bucket,
                         @Value("${kafka.topic.upload}") String uploadTopic){
        this.s3Client=s3Client;
        this.videoRepository=videoRepository;
        this.kafkaTemplate=kafkaTemplate;
        this.bucket=bucket;
        this.uploadTopic=uploadTopic;
    }

    public UploadResponse handleUpload(MultipartFile file, String uploaderId, String title) throws  Exception{
        String videoId = "v-"+ UUID.randomUUID().toString();
        String objectKey = String.format("upload/%s/raw-%s", videoId,file.getOriginalFilename());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        VideoDocument doc = VideoDocument.builder()
                .id(videoId)
                .uploaderId(uploaderId)
                .title(title)
                .status("UPLOADED")
                .sourcePath("manio://"+bucket+"/"+objectKey)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        String event = String.format("{\"videoId\":\"%s\",\"uploaderId\":\"%s\",\"objectPath\":\"%s\",\"timestamp\":%d}",
                videoId, uploaderId, objectKey, Instant.now().getEpochSecond());
        kafkaTemplate.send(uploadTopic, videoId, event);
        return new UploadResponse(videoId,"UPLOADED", objectKey);
    }


}
