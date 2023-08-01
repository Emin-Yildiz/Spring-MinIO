package com.example.minio.controller;

import com.example.minio.service.ImageService;
import org.simpleframework.xml.Path;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/img")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> imgUpload(@RequestParam MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(imageService.uploadImage(file), HttpStatus.CREATED);
    }

    /*
    Bu şekilde bir resim alma yöntemi kullanırsak resim yerine anlamsız sayılar gelir.
     */
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<Resource> imgDownload(@PathVariable String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//        return new ResponseEntity<>(imageService.downloadImg(fileName),HttpStatus.OK);
//    }

    /*
    bu metodda'ki gibi bir resim çekme işlemi yaparsak resimi anlamsız rakamlar yerine olduğu gibi görürüz.
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> imgDownload(@PathVariable String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        Resource resource = imageService.downloadImg(fileName);
        if (resource != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public String deleteImg(@PathVariable String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return imageService.removeImg(fileName);
    }
}
