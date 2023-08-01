package com.example.minio.controller;

import com.example.minio.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping("/create/{bucketName}")
    public ResponseEntity<String> createBucket(@PathVariable String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(bucketService.createBucket(bucketName), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{bucketName}")
    public ResponseEntity<String> deleteBucket(@PathVariable String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(bucketService.deleteBucket(bucketName),HttpStatus.OK);
    }
}
