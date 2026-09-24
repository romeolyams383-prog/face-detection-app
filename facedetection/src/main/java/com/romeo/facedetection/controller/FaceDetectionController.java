package com.romeo.facedetection.controller;

import com.romeo.facedetection.dto.FaceDetectionResponse;
import com.romeo.facedetection.service.FaceDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/faces")
public class FaceDetectionController {

    private final FaceDetectionService faceDetectionService;

    public FaceDetectionController(FaceDetectionService faceDetectionService) {
        this.faceDetectionService = faceDetectionService;
    }

    @PostMapping("/detect")
    public ResponseEntity<FaceDetectionResponse> detectFaces(@RequestParam("image") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            FaceDetectionResponse response = faceDetectionService.detectFaces(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}