package com.amadeusounds.model;

import com.amadeusounds.view.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity{
	
	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	private int rating;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yy")
	@JsonView(Views.SongBaseView.class)
	private LocalDate date;

	@ManyToOne
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JsonIgnore
	private Song song;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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
	
	
}
