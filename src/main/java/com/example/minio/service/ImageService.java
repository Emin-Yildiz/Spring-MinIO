package com.example.minio.service;

import io.minio.*;
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
public class ImageService {
    private final MinioClient minioClient;

    @Value("${spring.minio.image-bucket}")
    private String bucketName;

    public ImageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadImage(MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException { // upload'da request param alınıyor.
        String objectName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        try{
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build()
            );
            return "Image upload is succes";
        }catch (MinioException e){
            return "Image upload is fail";
        }
    }

    public Resource downloadImg(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
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

    public String removeImg(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
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
