package com.domogo.vcalfileupload.controller;

import com.domogo.vcalfileupload.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/uploadSequential")
    public void uploadSequential(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            fileStorageService.storeFileSequential(file);
        }
    }

}