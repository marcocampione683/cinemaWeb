package model;

import java.io.Serializable;

public class FilmBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title, type, duration, directedBy, story, photo, video;

	public FilmBean() {
		title = "";
		type = "";
		duration = "";
		directedBy = "";
		story = "";
		photo = "";
		video = "";
	}
	
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDirectedBy() {
		return directedBy;
	}

	public void setDirectedBy(String directedBy) {
		this.directedBy = directedBy;
	}
	
	@Override 
	public boolean equals(Object other) {
		return (this.getTitle().equals(((FilmBean)other).getTitle()));
	}

	@Override
	public String toString() {
		return "titolo= "+title+" genere= "+type+" durata= "+duration+" regia= "+directedBy+" trama= "+story;
	}

}
