package com.netflix.upload_service.modal;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Data
@Document(collection="VideoDocument")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDocument {
    @Id
    private String id;
    private String uploaderId;
    private String title;
    private String status;
    private String sourcePath;
    private List<Rendition> renditions;
    private Instant createdAt;
    private Instant updatedAt;

    private static class Rendition{
        private String resolution;
        private String manifestPath;
        private int segmentCount;
    }
}
