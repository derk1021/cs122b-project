package com.fabflix.service;

import java.util.List;

import com.fabflix.entity.Movie;

public interface MovieService {

	public List<Movie> getTopRatedMovies();

	public Movie getMovieDetails(String movieId);

	List<Movie> getAllMovies();

	List<Movie> findMovieByName(String Name);

	public List<Movie> findMovieByGenre(String genre);

}
