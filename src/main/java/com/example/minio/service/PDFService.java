package com.example.minio.service;


import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class PDFService {
    private final MinioClient minioClient;

    @Value("${spring.minio.pdf-bucket}")
    private String bucketName;

    public PDFService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadPDF(MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        try {
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, inputStream.available(),-1)
                            .build()
            );
            return "PDF upload succes";
        }catch (MinioException e){
            return "PDF upload is failed";
        }
    }

    public Resource downloadPDF(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try{
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
            return new InputStreamResource(inputStream);
        }catch (MinioException e){
            return null;
        }
    }

    public String removePDF(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
            return "Object remove from " + bucketName;
        }catch (MinioException e){
            return "Object not remove";
        }
    }
}
