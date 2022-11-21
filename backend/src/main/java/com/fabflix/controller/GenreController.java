package com.fabflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabflix.entity.Genres;
import com.fabflix.service.GenreService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class GenreController {

	@Autowired
	GenreService genreService;

	@GetMapping("/genre")
	public ResponseEntity<List<Genres>> getallGenres() {
		return ResponseEntity.ok(genreService.findAll());
	}
}
