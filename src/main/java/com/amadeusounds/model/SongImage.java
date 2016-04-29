package com.amadeusounds.model;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "song_images")
public class SongImage extends BaseEntity{
	
	@NotNull
	private Blob image;

	@NotNull
	private int timing;

	@NotNull
	@ManyToOne
	private Song song;

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public int getTiming() {
		return timing;
	}

	public void setTiming(int timing) {
		this.timing = timing;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
	
	
}
