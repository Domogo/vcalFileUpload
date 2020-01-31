package com.domogo.vcalfileupload.repository;

import java.util.Optional;

import com.domogo.vcalfileupload.model.File;

import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {

    Optional<File> findById(Long id);

    Optional<File> findByInProgress(boolean inProgress);

}