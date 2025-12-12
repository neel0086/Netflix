package com.netflix.upload_service.controller;

import com.netflix.upload_service.dto.UploadResponse;
import com.netflix.upload_service.service.UploadService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/videos")
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService=uploadService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UploadResponse> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "uploaderId", required = false, defaultValue = "uanon") String uploaderId,
            @RequestParam(value = "title", required = false, defaultValue = "Untitled") String title
    ){
        try{
            UploadResponse resp = uploadService.handleUpload(file,uploaderId, title);
            return ResponseEntity.status(201).body(resp);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(new UploadResponse(null, "ERROR", e.getMessage()));
        }
    }

}
