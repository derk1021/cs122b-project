package com.fabflix.service;

import java.util.List;

import com.fabflix.entity.Movie;
import com.fabflix.entity.Stars;
import com.fabflix.exception.EntityAlreadyExistsException;

public interface StarService {

	Stars getStarDetailsByStarId(String starId);

	List<Movie> getMovieDetails(String starId);

	Stars addStar(Stars star) throws EntityAlreadyExistsException;

}
