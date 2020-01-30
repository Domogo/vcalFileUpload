package com.domogo.vcalfileupload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.domogo.vcalfileupload.property.StorageProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private Path fileStorageLocation;

    @Autowired
    public void StorageProperties(StorageProperties storageProperties) {
        this.fileStorageLocation = Paths.get(storageProperties.getUploadLocation())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public void storeFileSequential(List<MultipartFile> files) {

        for (MultipartFile file : files) {
            // save file, get name, type and size and store in memory
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            try {
                if (fileName.contains("..")) {
                    throw new RuntimeException("Filename contains invalid path sequence" + fileName);
                }

                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation);
            } catch (IOException ex) {
                throw new RuntimeException("Could not store file " + fileName + ". Please try again.");
            }

        }
    }


    public void storeFile(MultipartFile file) {

        // save file, get name, type and size and store in memory
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Filename contains invalid path sequence" + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again.");
        }

    }

}