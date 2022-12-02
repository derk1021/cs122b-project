package com.fabflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fabflix.entity.Ratings;

@Repository
@Transactional(readOnly = true)
public interface RatingRepository extends JpaRepository<Ratings, String> {

}
