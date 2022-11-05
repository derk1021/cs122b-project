package com.fabflix.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class Stars {

	@Id
	@NotBlank
	@NotNull
	private String Id = "";

	@NotBlank
	@NotNull
	@Size(max = 100)
	private String name = "";

	private int birthYear;

	@ManyToMany(mappedBy = "stars", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Movie> movies;

}
