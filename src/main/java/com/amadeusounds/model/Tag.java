package com.amadeusounds.model;

import java.sql.Blob;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity{
	
	@Length(max  = 50)
	private String name;

	@JsonIgnore
	private Blob image;

	@ManyToMany(mappedBy = "tags")
	private List<Song> songs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}
	
	
}
