package com.netflix.upload_service.repository;

import com.netflix.upload_service.modal.VideoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<VideoDocument, String> {
}
