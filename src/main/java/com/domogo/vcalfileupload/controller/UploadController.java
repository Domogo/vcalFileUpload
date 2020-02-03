package com.domogo.vcalfileupload.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.domogo.vcalfileupload.model.FileRecord;
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


    @PostMapping(path = "multiple")
    public void uploadMultiple(@RequestParam("files") List<MultipartFile> files) {
        fileStorageService.storeFiles(files);
    }


    @GetMapping(path = "duration")
    public List<String> uploadDuration() {
        List<FileRecord> fileRecords = fileStorageService.findByInProgress(false);
        List<String> durationResults = new ArrayList<>();

        for (FileRecord fileRecord : fileRecords) {
            durationResults.add("upload_duration{id=" + fileRecord.getId() + "} " + fileRecord.getDuration());
        }

        return durationResults;
    }

    @GetMapping(path = "progress")
    public HashMap<String, List<ProgressDto>> getUploadProgress() {
        List<FileRecord> inProgress = fileStorageService.findByInProgress(true);
        List<ProgressDto> progressData = new ArrayList<>();

        for (FileRecord r : inProgress) {
           ProgressDto p = new ProgressDto(r.getId(), r.getFileSize(), r.getUploaded());
           progressData.add(p);
        }

        HashMap<String, List<ProgressDto>> response = new HashMap<>();
        response.put("uploads", progressData);

        return response;
    }

}