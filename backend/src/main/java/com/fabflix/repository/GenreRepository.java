package com.fabflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fabflix.entity.Genres;

@Repository
@Transactional(readOnly = true)
public interface GenreRepository extends JpaRepository<Genres, Integer> {

	List<Genres> findAllByOrderByName();

}
