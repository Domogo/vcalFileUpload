package com.domogo.vcalfileupload.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    private long file;

    // How much has been uploaded so far
    private long uploaded;

    private boolean inProgress;

    public File() {}

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFile() {
        return this.file;
    }

    public void setFile(long file) {
        this.file = file;
    }

    public long getUploaded() {
        return this.uploaded;
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

    public float getDurationTime() {
        return this.durationTime;
    }

    public void setDurationTime(float durationTime) {
        this.durationTime = durationTime;
    }

    private float durationTime;

}