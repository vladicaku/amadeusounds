package com.amadeusounds.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.amadeusounds.view.SongView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{
	
	@Length(max = 255)
	private String comment;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yy")
	@JsonView(SongView.BaseView.class)
	private LocalDate date;


	@ManyToOne
	@JsonIgnore
	private Song song;

	@ManyToOne
	@JsonIgnore
	private User user;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
