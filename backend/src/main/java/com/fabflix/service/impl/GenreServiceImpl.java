package com.fabflix.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabflix.entity.Genres;
import com.fabflix.repository.GenreRepository;
import com.fabflix.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	GenreRepository genreRepository;

	@Override
	public List<Genres> findAll() {
		return genreRepository.findAllByOrderByName();
	}

}
