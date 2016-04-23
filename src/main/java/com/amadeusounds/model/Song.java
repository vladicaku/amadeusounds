package com.amadeusounds.model;

import com.amadeusounds.view.SongView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Entity
@Table(name = "songs")
public class Song extends BaseEntity {

    @Length(max = 50)
    @JsonView(SongView.BaseView.class)
    private String name;

    @NotNull
    @JsonIgnore
    private Blob song;

    @Length(max = 200)
    @JsonView(SongView.BaseView.class)
    private String description;

    @OneToMany(mappedBy = "song")
    @JsonView(SongView.BaseView.class)
    private List<SongImage> images;

    @ManyToOne
    @JsonView(SongView.BaseView.class)
    private Category category;

    @ManyToMany()
    @JsonView(SongView.BaseView.class)
    private List<Tag> tags;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yy")
    @JsonView(SongView.BaseView.class)
    private LocalDate date;

    @ManyToOne
    @JsonView(SongView.BaseView.class)
    private User user;

    @OneToMany
    @JsonView(SongView.SummaryView.class)
    private List<Comment> comments;

    @OneToMany
    private List<Rating> ratings;

    @JsonView(SongView.BaseView.class)
    private double rating;

    @JsonView(SongView.BaseView.class)
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
