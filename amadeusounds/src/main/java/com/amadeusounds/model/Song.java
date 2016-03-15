package com.amadeusounds.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Entity
@Table(name = "songs")
public class Song extends BaseEntity {
    @Length(max = 50)
    String name;

    @Length(max = 200)
    String description;

    @ManyToOne
    User user;

    @ElementCollection(targetClass = Blob.class)
    List<Blob> images;

    @NotNull
    Blob song;

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

    public List<Blob> getImages() {
        return images;
    }

    public void setImages(List<Blob> images) {
        this.images = images;
    }

    public Blob getSong() {
        return song;
    }

    public void setSong(Blob song) {
        this.song = song;
    }
}
