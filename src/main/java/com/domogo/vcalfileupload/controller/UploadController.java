package com.domogo.vcalfileupload.controller;

import java.util.List;

import com.domogo.vcalfileupload.model.FileRecord;
import com.domogo.vcalfileupload.service.FileStorageService;

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


    @PostMapping(path = "multiple")
    public void uploadMultiple(@RequestParam("files") List<MultipartFile> files) {
        fileStorageService.storeFiles(files);
    }


    @GetMapping(path = "duration")
    public List<FileRecord> uploadDuration() {
        return fileStorageService.findByInProgress(false);
    }

    @GetMapping(path = "progress")
    public List<Object[]> getUploadProgress() {
        return fileStorageService.getUploadProgress();
    }

}