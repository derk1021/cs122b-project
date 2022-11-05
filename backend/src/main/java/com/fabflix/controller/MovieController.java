package com.fabflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<List<Movie>> createUser() {

		return ResponseEntity.ok(movieService.getTopRatedMovies());
	}

}
