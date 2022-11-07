package com.fabflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fabflix.entity.Movie;
import com.fabflix.service.MovieService;

@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class MovieController {
	
	@Autowired
	MovieService movieService;

	@GetMapping("/movie")
	public ResponseEntity<List<Movie>> getTopRatedMovies() {

		return ResponseEntity.ok(movieService.getTopRatedMovies());
	}

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<Movie> getMovieDetails(@PathVariable(name = "movieId") String movieId) {

		return ResponseEntity.ok(movieService.getMovieDetails(movieId));
	}

	@GetMapping("/movie/all")
	public ResponseEntity<List<Movie>> getAllMovies() {

		return ResponseEntity.ok(movieService.getTopRatedMovies());
	}

	@GetMapping("/movie/title/{name}")
	public ResponseEntity<List<Movie>> findMovieByName(@PathVariable(name = "name") String name) {
		return ResponseEntity.ok(movieService.findMovieByName(name));
	}

	@GetMapping("/movie/genre/{genre}")
	public ResponseEntity<List<Movie>> findMovieByGenre(@PathVariable(name = "genre") String genre) {
		return ResponseEntity.ok(movieService.findMovieByGenre(genre));
	}

}
