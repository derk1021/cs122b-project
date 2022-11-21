package com.fabflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabflix.entity.Genres;

@Repository
public interface GenreRepository extends JpaRepository<Genres, Integer> {

	List<Genres> findAllByOrderByName();

}
