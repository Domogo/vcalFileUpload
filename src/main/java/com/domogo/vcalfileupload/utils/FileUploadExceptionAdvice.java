package com.domogo.vcalfileupload.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    private long MAX_UPLOAD_SIZE = 52428800;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException() {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("The file you're trying to send is too large. Max size is: " + MAX_UPLOAD_SIZE + " bytes.");
    }
}