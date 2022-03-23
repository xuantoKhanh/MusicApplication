package com.example.musicapplication;

import java.util.List;

public class ResponseMusic {
	private List<String> sources;
	private String thumb;
	private String subtitle;
	private String description;
	private String title;

	public List<String> getSources(){
		return sources;
	}

	public String getThumb(){
		return thumb;
	}

	public String getSubtitle(){
		return subtitle;
	}

	public String getDescription(){
		return description;
	}

	public String getTitle(){
		return title;
	}
}