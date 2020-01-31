package com.domogo.vcalfileupload.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class File {

    @Id
    private String id;

    @NotBlank
    private String name;

    private long fileSize;

    // How much of the file has been uploaded so far
    private long uploaded = 0;

    private boolean inProgress = true;

    private long duration = 0;

    public File() {}

    public String getId() {
        return this.id;
    }

    public void setId(String string) {
        this.id = string;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUploaded() {
        return this.uploaded;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setUploaded(long uploaded) {
        this.uploaded = uploaded;
    }

    public boolean isInProgress() {
        return this.inProgress;
    }

    public boolean getInProgress() {
        return this.inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

}