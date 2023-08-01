package com.example.minio.controller;

import com.example.minio.service.PDFService;
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
@RequestMapping("/pdf")
public class PDFController {

    private final PDFService pdfService;

    public PDFController(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> getPdfFile(@PathVariable String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        Resource resource = pdfService.downloadPDF(fileName);
        if (resource != null){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPDF(@RequestParam MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new ResponseEntity<>(pdfService.uploadPDF(file), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{fileName}")
    public String deletePDF(@PathVariable String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return pdfService.removePDF(fileName);
    }
}
