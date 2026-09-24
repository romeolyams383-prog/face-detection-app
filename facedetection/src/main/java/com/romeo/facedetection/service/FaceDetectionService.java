package com.romeo.facedetection.service;

import com.romeo.facedetection.dto.FaceDetectionResponse;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class FaceDetectionService {

    private final CascadeClassifier faceCascade;

    public FaceDetectionService() throws IOException {
        // Chargement du fichier xml depuis le classpath vers un fichier temporaire
        ClassPathResource resource = new ClassPathResource("cascades/haarcascade_frontalface_default.xml");
        File tempFile = File.createTempFile("haarcascade", ".xml");
        tempFile.deleteOnExit();

        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        this.faceCascade = new CascadeClassifier(tempFile.getAbsolutePath());
        if (this.faceCascade.empty()) {
            throw new RuntimeException("Impossible de charger le classificateur Haar Cascade.");
        }
    }

    public FaceDetectionResponse detectFaces(MultipartFile file) throws IOException {
        // 1. Lire les octets de l'image
        byte[] bytes = file.getBytes();
        Mat image = opencv_imgcodecs.imdecode(new Mat(bytes), opencv_imgcodecs.IMREAD_COLOR);

        if (image.empty()) {
            throw new IllegalArgumentException("Format d'image invalide ou corrompu.");
        }

        // 2. Détection des visages
        RectVector faces = new RectVector();
        faceCascade.detectMultiScale(image, faces);

        int facesCount = (int) faces.size();

        // 3. Dessiner les rectangles autour des visages détectés
        for (long i = 0; i < facesCount; i++) {
            Rect rect = faces.get(i);
            opencv_imgproc.rectangle(
                image,
                new Point(rect.x(), rect.y()),
                new Point(rect.x() + rect.width(), rect.y() + rect.height()),
                new Scalar(0, 0, 255, 0), // Rouge en BGR
                3,
                opencv_imgproc.LINE_8,
                0
            );
        }

        // 4. Encodage de l'image annotée en Base64
        BytePointer buffer = new BytePointer();
        opencv_imgcodecs.imencode(".png", image, buffer);
        byte[] imageBytes = new byte[(int) buffer.limit()];
        buffer.get(imageBytes);

        String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);

        return new FaceDetectionResponse(base64Image, facesCount);
    }
}