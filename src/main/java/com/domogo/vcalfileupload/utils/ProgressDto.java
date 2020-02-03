package com.domogo.vcalfileupload.utils;

public class ProgressDto {

    private final String id;
    private final long fileSize;
    private final long uploaded;


    public ProgressDto(String id, long fileSize, long uploaded) {
        this.id = id;
        this.fileSize = fileSize;
        this.uploaded = uploaded;
    }


    public String getId() {
        return this.id;
    }


    public long getFileSize() {
        return this.fileSize;
    }


    public long getUploaded() {
        return this.uploaded;
    }

}