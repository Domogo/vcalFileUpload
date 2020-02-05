package com.domogo.vcalfileupload.controller;

import java.util.HashMap;
import java.util.List;

import com.domogo.vcalfileupload.service.FileStorageService;
import com.domogo.vcalfileupload.utils.ProgressDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public void upload(@RequestHeader("XUpload-File") String fileName, @RequestParam("file") MultipartFile file) {
        fileStorageService.storeFile(file, fileName);
    }

    @GetMapping(path = "duration")
    public List<String> uploadDuration() {
        return fileStorageService.uploadDuration();
    }

    @GetMapping(path = "progress")
    public HashMap<String, List<ProgressDto>> getUploadProgress() {
        return fileStorageService.getUploadProgress();
    }

}