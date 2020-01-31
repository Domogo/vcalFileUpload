package com.domogo.vcalfileupload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import com.domogo.vcalfileupload.model.File;
import com.domogo.vcalfileupload.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageService {

    @Autowired
    FileRepository fileRepository;

    private Path fileStorageLocation = Paths.get(System.getProperty("java.io.tmpdir"))
        .toAbsolutePath().normalize();


    public void storeFiles(List<MultipartFile> files) {

        for (MultipartFile file : files) {
            storeFile(file);
        }
    }


    public void storeFile(MultipartFile file) {

        long startTime = System.currentTimeMillis();

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No file present in request.");
        }

        // save file, get name, type and size and store in memory
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Filename contains invalid path sequence" + fileName);
            }

            Date date = new Date();
            long timestamp = date.getTime();
            // create a db record
            File fileData = new File();
            fileData.setId(fileName + '-' + timestamp);
            fileData.setFileSize(file.getSize());
            fileData.setName(fileName);
            fileData = saveOrUpdate(fileData);

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // before writing, delete file if exists.
            Files.deleteIfExists(targetLocation);
            Files.copy(file.getInputStream(), targetLocation);

            long duration = System.currentTimeMillis() - startTime;
            // update that db record with upload duration and set inProgress to false
            fileData.setDuration(duration);
            fileData.setInProgress(false);
            saveOrUpdate(fileData);

        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Could not store file " + fileName + ". Please try again.");
        }

    }

    public File saveOrUpdate(File file) {
        return fileRepository.save(file);
    }

    public List<File> findAll() {
        return fileRepository.findAll();
    }

}