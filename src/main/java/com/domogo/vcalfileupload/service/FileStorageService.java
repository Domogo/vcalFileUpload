package com.domogo.vcalfileupload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import com.domogo.vcalfileupload.model.FileRecord;
import com.domogo.vcalfileupload.repository.FileRepository;
import com.domogo.vcalfileupload.utils.WriteFileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageService {

    @Autowired
    FileRepository fileRepository;

    private long ACTIVE_LIMIT = 100;

    private Path fileStorageLocation = Paths.get(System.getProperty("java.io.tmpdir")).toAbsolutePath().normalize();

    public void storeFiles(List<MultipartFile> files) {

        for (MultipartFile file : files) {
            storeFile(file, null);
        }
    }

    /*
     * fileName is Nullable because of the case when multiple files are sent with
     * one request - can't set a header for multiple files
     */
    public void storeFile(MultipartFile file, @Nullable String fileName) {

        // get start time, we have to track upload duration
        long startTime = System.currentTimeMillis();

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No file present in request.");
        }

        if (fileName.isEmpty()) {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
        }
        // if file name from header doesn't have file extension but
        // multipart file name does - assign that extension to file name from header
        if (!fileName.contains(".") && file.getOriginalFilename().contains(".")) {
            fileName = fileName + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        }

        List<String> filesCurrentlyUploading = fileRepository.getFileNamesInProgress();
        long activeUploadsCount = fileRepository.countByInProgress(true);

        if (filesCurrentlyUploading.contains(fileName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "File " + fileName + " is currently uploading.");
        }

        if (activeUploadsCount >= ACTIVE_LIMIT) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Upload count limit exceeded, try again in a moment!");
        }

        try {
            if (fileName.contains("..")) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Filename contains invalid path sequence" + fileName);
            }

            Date date = new Date();
            long timestamp = date.getTime();
            // create a db record
            FileRecord fileRecord = new FileRecord();
            fileRecord.setId(fileName + '-' + timestamp);
            fileRecord.setFileSize(file.getSize());
            fileRecord.setName(fileName);
            fileRecord = saveOrUpdate(fileRecord);

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // before writing, delete file if exists.
            Files.deleteIfExists(targetLocation);
            WriteFileUtil.copyInputStreamToFile(file.getInputStream(), targetLocation.toFile(), fileRecord);

            // update that db record with upload duration and set inProgress to false
            fileRecord.setDuration(System.currentTimeMillis() - startTime);
            fileRecord.setInProgress(false);
            saveOrUpdate(fileRecord);

        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Could not store file " + fileName + ". Please try again.");
        }

    }

    public FileRecord saveOrUpdate(FileRecord file) {
        return fileRepository.save(file);
    }

    public List<FileRecord> findAll() {
        return fileRepository.findAll();
    }

    public List<FileRecord> findByInProgress(boolean inProgress) {
        return fileRepository.findByInProgress(inProgress);
    }

    public List<Object[]> getUploadProgress() {
        return fileRepository.getUploadProgress();
    }

}