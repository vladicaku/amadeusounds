package com.amadeusounds.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Entity
@Table(name = "songs")
public class Song extends BaseEntity {
    @Length(max = 50)
    private String name;
    
    @NotNull
    private Blob song;

    @Length(max = 200)
    private String description;

    @OneToMany
    private List<SongImage> images;
    
    @ManyToOne
    private Category category;
    
    @ManyToMany (fetch = FetchType.EAGER)
    private List<Tag> tags;
    
    @NotNull
    private Date date;
    
    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> comments;
    
    @OneToMany
    private List<Rating> ratings;
    
    @Transient
    private Long views;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Blob getSong() {
        return song;
    }

    public void setSong(Blob song) {
        this.song = song;
    }

	public List<SongImage> getImages() {
		return images;
	}

	public void setImages(List<SongImage> images) {
		this.images = images;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Date getDate() {
		return date;
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}
    
    
}
