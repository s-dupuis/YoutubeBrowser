package com.kixot.youtubebrowser.models;

import java.io.Serializable;

public class Download {

    private long id;
    private String title;
    private int progress;
    private String type;

    public Download() {}

    public Download(String title, int progress, String type) {
        this.title = title;
        this.progress = progress;
        this.type = type;
    }

    public Download(long id, String title, int progress, String type) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.type = type;
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
}
