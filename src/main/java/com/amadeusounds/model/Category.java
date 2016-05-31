package com.amadeusounds.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
	
	@Length(max  = 50)
	private String name;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Song> songs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
