package com.domogo.vcalfileupload.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class StorageProperties {

    private String uploadLocation;

    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

}