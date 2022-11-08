package com.fabflix.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fabflix.entity.Movie;
import com.fabflix.entity.MovieDto;
import com.fabflix.entity.Ratings;
import com.fabflix.repository.MovieRepository;
import com.fabflix.repository.RatingRepository;
import com.fabflix.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<MovieDto> getTopRatedMovies() {

		List<MovieDto> movies = new ArrayList<>();
		Page<Ratings> topRated = ratingRepository
				.findAll(PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "rating")));
		for (Ratings i : topRated.getContent()) {
			Optional<Movie> movie = movieRepository.findById(i.getMovieId());
			if (movie.isPresent()) {
				MovieDto movieDto = modelMapper.map(movie.get(), MovieDto.class);
				movieDto.setRating(i.getRating());
				movies.add(movieDto);
			}
		}
		return movies;
	}

	@Override
	public List<MovieDto> findMovieByName(String name) {
		List<MovieDto> movies = new ArrayList<>();
		List<Movie> moviesList = movieRepository.findByTitleStartsWith(name);
		List<MovieDto> movieDtoList = moviesList.stream().map(m -> modelMapper.map(m, MovieDto.class))
				.collect(Collectors.toList());
		for (MovieDto i : movieDtoList) {
			Optional<Ratings> rating = ratingRepository.findById(i.getId());
			if (rating.isPresent()) {
				i.setRating(rating.get().getRating());
			}
		}
		return movieDtoList;
	}

	@Override
	public List<MovieDto> findMovieByGenre(String name) {
		List<MovieDto> movies = new ArrayList<>();
		List<Movie> moviesList = movieRepository.findByGenresName(name);
		List<MovieDto> movieDtoList = moviesList.stream().map(m -> modelMapper.map(m, MovieDto.class))
				.collect(Collectors.toList());
		for (MovieDto i : movieDtoList) {
			Optional<Ratings> rating = ratingRepository.findById(i.getId());
			if (rating.isPresent()) {
				i.setRating(rating.get().getRating());
			}
		}
		return movieDtoList;
	}

	@Override
	public MovieDto getMovieDetails(String movieId) {
		Optional<Movie> movie = movieRepository.findById(movieId);
		if (movie.isEmpty()) {
			throw new EntityNotFoundException("Movie Not Found");
		}
		MovieDto movieDto = modelMapper.map(movie.get(), MovieDto.class);
		Optional<Ratings> rating = ratingRepository.findById(movieDto.getId());
		if (rating.isPresent()) {
			movieDto.setRating(rating.get().getRating());
		}

		return movieDto;
	}

}
