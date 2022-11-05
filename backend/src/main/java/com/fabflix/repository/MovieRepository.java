package com.fabflix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabflix.entity.Movie;
import com.fabflix.entity.Stars;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
	Optional<Movie> findByStars(Stars star);

}
