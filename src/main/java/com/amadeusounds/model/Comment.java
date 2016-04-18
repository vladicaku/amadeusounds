package com.amadeusounds.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{
	
	@Length(max = 255)
	private String comment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, insertable=true, updatable=true, columnDefinition = "default CURRENT_TIMESTAMP")
	private Date date;

	@PrePersist
	public void onCreate(){
		this.date = new Date();
	}

	@PreUpdate
	public void onPersist(){
		this.date = new Date();
	}

	@JsonIgnore
	@ManyToOne
	private Song song;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date.toString();
	}

	public void setDate(Date date) {
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

	@JsonProperty
	public Long getUserId() {
		return user.getId();
	}
	@JsonProperty
	public Long getSongId() {
		return song.getId();
	}


}
