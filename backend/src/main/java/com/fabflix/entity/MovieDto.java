package com.fabflix.entity;

import java.util.List;

import lombok.Data;

@Data
public class MovieDto {
	private String id;

	private String title;

	private int year;

	private String director;

	private float rating;

	private List<Genres> genres;

	private List<Stars> stars;
}
