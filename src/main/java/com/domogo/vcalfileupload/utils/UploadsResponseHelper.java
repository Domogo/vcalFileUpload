package com.domogo.vcalfileupload.utils;

public class UploadsResponseHelper {

    private final long id;
    private final long fileSize;
    private final long uploaded;


    public UploadsResponseHelper(long id, long fileSize, long uploaded) {
        this.id = id;
        this.fileSize = fileSize;
        this.uploaded = uploaded;
    }

    public long getId() {
        return this.id;
    }


    public long getFileSize() {
        return this.fileSize;
    }


    public long getUploaded() {
        return this.uploaded;
    }


}