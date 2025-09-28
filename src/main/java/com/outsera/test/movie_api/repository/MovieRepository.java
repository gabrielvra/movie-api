package com.outsera.test.movie_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsera.test.movie_api.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
