package com.example.musicapplication;

public class TrackFiles {
    private String description;
    private String sources;
    private String subtitle;
    private String thumb;
    private String title;


    public TrackFiles(String description, String sources, String subtitle, String thumb, String title) {
        this.description = description;
        this.sources = sources;
        this.subtitle = subtitle;
        this.thumb = thumb;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}