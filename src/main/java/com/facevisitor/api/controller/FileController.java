package com.facevisitor.api.controller;

import com.facevisitor.api.service.file.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api/v1/file")
@AllArgsConstructor
public class FileController {

    FileService fileService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        log.debug("file : {}",multipartFile.getName());
        String url = fileService.uploadImage(multipartFile);
        return ResponseEntity.ok(url);
    }


}
