package com.example.minio.service;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.errors.MinioException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class BucketService {

    private final MinioClient minioClient;

    public BucketService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String createBucket(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try{
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(bucketName)
                            .build()
            );
            return "Bucket created";
        }catch (MinioException e){
            return "Bucket not created";
        }

    }

    public String deleteBucket(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            minioClient.removeBucket(
                    RemoveBucketArgs
                            .builder()
                            .bucket(bucketName)
                            .build()
            );
        }catch (MinioException e){
            return "Bucket is not deleted";
        }
        return null;
    }
}
