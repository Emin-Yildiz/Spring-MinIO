package com.example.minio.controller;

import com.example.minio.service.VideoService;
import io.minio.errors.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(videoService.uploadVideo(file), HttpStatus.CREATED);
    }

    // videoyu tarayıcı üzerinden izleyebilmek için
    @GetMapping("/showVideo/{fileName}")
    public ResponseEntity<Resource> showVideo(@PathVariable String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        InputStreamResource resource = videoService.downloadVideo(fileName);
        if(resource != null){
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4")) // bu yöntem videoyu tarayıcıdan oynatıyor.
                    .body(resource);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    // videoyu cihaza indirebilmek için
    @GetMapping("/downloadVideo/{fileName}")
    public ResponseEntity<Resource> downloadVideo(@PathVariable String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        InputStreamResource resource = videoService.downloadVideo(fileName);
        if(resource != null){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // bu yöntem ise endpoint'e girince otomatik olarak videoyu indiriyor
                    //.contentType(MediaType.parseMediaType("video/mp4")) // bu yöntem videoyu tarayıcıdan oynatıyor.
                    // en son hangi contentType var ise o contentType geçerli olur.
                    .body(resource);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteVideo(@PathVariable String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return new ResponseEntity<>(videoService.deleteVideo(fileName),HttpStatus.OK);
    }
}
