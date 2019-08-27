package com.kixot.youtubebrowser.models;

public class Download {

    private String title;
    private int progress;
    private String type;

    public Download(String title, int progress, String type) {
        this.title = title;
        this.progress = progress;
        this.type = type;
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
