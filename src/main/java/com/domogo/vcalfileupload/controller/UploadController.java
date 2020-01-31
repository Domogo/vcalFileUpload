package com.domogo.vcalfileupload.controller;

import java.util.List;

import com.domogo.vcalfileupload.model.File;
import com.domogo.vcalfileupload.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/upload")
@RestController
public class UploadController {

    @Autowired
    FileStorageService fileStorageService;


    @PostMapping
    public void upload(@RequestParam("file") MultipartFile file) {
        fileStorageService.storeFile(file);
    }


    @PostMapping(path = "multiple")
    public void uploadMultiple(@RequestParam("files") List<MultipartFile> files) {
        fileStorageService.storeFiles(files);
    }


    @GetMapping(path = "duration")
    public List<File> uploadDuration() {
        return fileStorageService.findAll();
    }

}