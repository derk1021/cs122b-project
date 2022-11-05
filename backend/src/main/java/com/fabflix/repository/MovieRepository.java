package com.fabflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabflix.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

}
