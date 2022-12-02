package com.fabflix.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
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

	@Autowired
	ApplicationContext context;

	@Override
	public List<MovieDto> findMovieByName(String name) {
		List<Movie> moviesList = movieRepository.findByTitleStartsWithOrderByTitleAsc(name);
		return movieToDto(moviesList);
	}

	@Override
	public List<MovieDto> findMovieByGenre(String name) {
		List<Movie> moviesList = movieRepository.findByGenresNameOrderByTitleAsc(name);
		return movieToDto(moviesList);
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

	@Override
	public List<MovieDto> findByCriteria(String title, int year, String director, String starName) {
		long startTimeTS = System.nanoTime();
		long elapsedTimeTJ = -1;
		List<Movie> result = null;

		long startTimeTJ = System.nanoTime();
		if (!starName.equals("null")) {
			result = movieRepository.findByStarsNameContainingIgnoreCase(starName);
			for (Movie movie : result) {
				if (!director.equals("null") && !StringUtils.containsIgnoreCase(movie.getDirector(), director)) {
					result.remove(movie);
				}
				if (!title.equals("null") && !StringUtils.containsIgnoreCase(movie.getTitle(), title)) {
					result.remove(movie);
				}
				if (year != 0 && movie.getYear() != year) {
					result.remove(movie);
				}
			}
		}
		else {

			result = movieRepository.findAll(new Specification<Movie>() {
				@Override
				public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<>();

					if (!title.equals("null")) {
						String[] prefixes = title.split(" ");
						for (String prefix : prefixes) {
							predicates.add(
							criteriaBuilder.and(
									criteriaBuilder.or(criteriaBuilder.like(root.get("title"), "% " + prefix + "%"),
											criteriaBuilder.like(root.get("title"), prefix + "%"),
													criteriaBuilder.like(root.get("title"), "% " + prefix))));
						}
					}
					if (year != 0) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("year"), year)));
					}
					if (!director.equals("null")) {
						predicates.add(
								criteriaBuilder.and(criteriaBuilder.like(root.get("director"), "%" + director + "%")));
					}
					;
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			});

		}
		long endTimeTJ = System.nanoTime();

		elapsedTimeTJ = endTimeTJ - startTimeTJ;
		
		List<MovieDto> movieToDto = movieToDto(result);
		long endTimeTS = System.nanoTime();

		long elapsedTimeTS = endTimeTS - startTimeTS;

		System.out.println(elapsedTimeTJ);

		System.out.println(elapsedTimeTS);

		try {
			File file = new File("../logs/log.txt");
			if (file.createNewFile()) {
				FileWriter myWriter = new FileWriter(file.getName());
				myWriter.write("TS : " + elapsedTimeTS + ", TJ : " + elapsedTimeTJ + "\n");
				myWriter.close();
			} else {
				FileWriter myWriter = new FileWriter(file, true);
				myWriter.write("TS : " + elapsedTimeTS + ", TJ : " + elapsedTimeTJ + "\n");
				myWriter.close();

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return movieToDto;
	}

	private List<MovieDto> movieToDto(List<Movie> moviesList) {
		return moviesList.stream().map(new Function<Movie, MovieDto>() {

            @Override
			public MovieDto apply(Movie t) {
				Optional<Ratings> rating = ratingRepository.findById(t.getId());
				MovieDto dto = modelMapper.map(t, MovieDto.class);
				if (rating.isPresent()) {
					dto.setRating(rating.get().getRating());
				}
				return dto;
            }

		}).collect(Collectors.toList());
	}

}
