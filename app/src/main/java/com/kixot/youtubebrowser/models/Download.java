package com.kixot.youtubebrowser.models;

import java.io.Serializable;

public class Download implements Serializable{

    private long id;
    private String title;
    private int progress;
    private String type;
    private String status;

    public Download() {}

    public Download(String title, int progress, String type) {
        this.title = title;
        this.progress = progress;
        this.type = type;
        this.status = "downloading";
    }

    public Download(long id, String title, int progress, String type, String status) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.type = type;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
