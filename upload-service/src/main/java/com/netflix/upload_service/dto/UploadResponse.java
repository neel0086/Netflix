package com.netflix.upload_service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
    private String videoId;
    private String status;
    private String objectPathError;

}
