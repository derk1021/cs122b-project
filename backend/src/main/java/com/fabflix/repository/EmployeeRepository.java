package com.fabflix.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fabflix.entity.Employee;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	@Transactional(readOnly = true)
	Employee findByEmail(String email);

	@Transactional(readOnly = false)
	@Query(value = " call add_movie(:movieTitle,:movieYear,:movieDirector,:starName,:genreName)", nativeQuery = true)
	Map<String, Object> addMovie(@Param("movieTitle") String movieTitle, @Param("movieYear") int movieYear,
			@Param("movieDirector") String movieDirector, @Param("starName") String starName,
			@Param("genreName") String genreName);

	@Transactional(readOnly = true)
	@Query(value = "show tables", nativeQuery = true)
	List<String> describeTables();

	@Transactional(readOnly = true)
	@Query(value = "describe movies", nativeQuery = true)
	List<Map<String, Object>> describeMovies();

	@Transactional(readOnly = true)
	@Query(value = "describe stars", nativeQuery = true)
	List<Map<String, Object>> describeStars();

	@Transactional(readOnly = true)
	@Query(value = "describe stars_in_movies", nativeQuery = true)
	List<Map<String, Object>> describeStarsInMovies();

	@Transactional(readOnly = true)
	@Query(value = "describe genres", nativeQuery = true)
	List<Map<String, Object>> describeGenre();

	@Transactional(readOnly = true)
	@Query(value = "describe genres_in_movies", nativeQuery = true)
	List<Map<String, Object>> describeGenresInMovies();

	@Transactional(readOnly = true)
	@Query(value = "describe customers", nativeQuery = true)
	List<Map<String, Object>> describeCustomers();

	@Transactional(readOnly = true)
	@Query(value = "describe sales", nativeQuery = true)
	List<Map<String, Object>> describeSales();

	@Transactional(readOnly = true)
	@Query(value = "describe creditcards", nativeQuery = true)
	List<Map<String, Object>> describeCreditCards();

	@Transactional(readOnly = true)
	@Query(value = "describe ratings", nativeQuery = true)
	List<Map<String, Object>> describeRatings();

	@Transactional(readOnly = true)
	@Query(value = "describe employees", nativeQuery = true)
	List<Map<String, Object>> describeEmployees();

}
