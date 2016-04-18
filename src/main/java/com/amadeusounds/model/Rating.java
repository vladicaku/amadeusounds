package com.amadeusounds.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity{
	
	@NotNull
	private int rating;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, insertable=true, updatable=true, columnDefinition = "default CURRENT_TIMESTAMP")
	private Date date;

	@PrePersist
	public void onCreate(){
		this.date = new java.util.Date();
	}

	@PreUpdate
	public void onPersist(){
		this.date = new java.util.Date();
	}


	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@ManyToOne
	private Song song;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDate() {
		return date.toString();
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	@JsonProperty
	public Long getUserId() {
		return user.getId();
	}

	@JsonProperty
	public Long getSongId() {
		return song.getId();
	}
	
}
