package com.fabflix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fabflix.entity.Movie;
import com.fabflix.entity.Stars;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
	Optional<Movie> findByStars(Stars star);

	@Query(value = "select max(substring(id, 3)) from movies", nativeQuery = true)
	int findLastId();

	@Query(value = "select max(substring(id, 3)) from genres", nativeQuery = true)
	int findLastGenreId();

	List<Movie> findByTitleStartsWithOrderByTitleAsc(String title);

	List<Movie> findByGenresNameOrderByTitleAsc(String genre);

	List<Movie> findByStarsNameContainingIgnoreCase(String starName);

	List<Movie> findAll(Specification<Movie> specification);

}
