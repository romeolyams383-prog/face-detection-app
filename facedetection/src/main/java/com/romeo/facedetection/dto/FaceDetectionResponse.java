package com.romeo.facedetection.dto;

public class FaceDetectionResponse {
    private String base64Image;
    private int facesCount;

    public FaceDetectionResponse() {
    }

    public FaceDetectionResponse(String base64Image, int facesCount) {
        this.base64Image = base64Image;
        this.facesCount = facesCount;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public int getFacesCount() {
        return facesCount;
    }

    public void setFacesCount(int facesCount) {
        this.facesCount = facesCount;
    }
}
