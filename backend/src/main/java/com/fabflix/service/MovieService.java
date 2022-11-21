package com.fabflix.service;

import java.util.List;

import com.fabflix.entity.MovieDto;

public interface MovieService {

	public MovieDto getMovieDetails(String movieId);

	List<MovieDto> findMovieByName(String Name);

	public List<MovieDto> findMovieByGenre(String genre);

	List<MovieDto> findByCriteria(String title, int year, String director, String starName);

}
