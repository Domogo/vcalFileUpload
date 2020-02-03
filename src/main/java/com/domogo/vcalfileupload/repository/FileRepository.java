package com.domogo.vcalfileupload.repository;

import java.util.List;
import java.util.Optional;

import com.domogo.vcalfileupload.model.FileRecord;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<FileRecord, Long> {

    Optional<FileRecord> findById(Long id);

    List<FileRecord> findByInProgress(boolean inProgress);

    List<FileRecord> findAll();

    @Query("SELECT id, fileSize, uploaded FROM FileRecord WHERE inProgress = TRUE")
    List<Object[]> getUploadProgress();

    @Query("SELECT name FROM FileRecord WHERE inProgress = TRUE")
    List<String> getFileNamesInProgress();

    long countByInProgress(boolean inProgress);

}