package com.fabflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fabflix.entity.MovieDto;
import com.fabflix.service.MovieService;

@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class MovieController {
	
	@Autowired
	MovieService movieService;

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<MovieDto> getMovieDetails(@PathVariable(name = "movieId") String movieId) {

		return ResponseEntity.ok(movieService.getMovieDetails(movieId));
	}

	@GetMapping("/movie/title/{name}")
	public ResponseEntity<List<MovieDto>> findMovieByName(@PathVariable(name = "name") String name) {
		return ResponseEntity.ok(movieService.findMovieByName(name));
	}

	@GetMapping("/movie/genre/{genre}")
	public ResponseEntity<List<MovieDto>> findMovieByGenre(@PathVariable(name = "genre") String genre) {
		return ResponseEntity.ok(movieService.findMovieByGenre(genre));
	}

	@GetMapping("/movie")
	public ResponseEntity<List<MovieDto>> findMovieByCriteria(@RequestParam(name = "title") String title,
			@RequestParam(name = "year") int year, @RequestParam(name = "director") String director,
			@RequestParam(name = "star") String star) {
		return ResponseEntity.ok(movieService.findByCriteria(title, year, director, star));
	}
}
