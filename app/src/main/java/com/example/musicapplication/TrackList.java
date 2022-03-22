package com.example.musicapplication;

import java.util.ArrayList;

public class TrackList {
    private String description;
    private ArrayList<String> sources;
    private String subtitle;
    private String thumb;
    private String title;

    public TrackList(String description, ArrayList<String> sources, String subtitle, String thumb, String title) {
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

    public ArrayList<String> getSources() {
        return sources;
    }

    public void setSources(ArrayList<String> sources) {
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
