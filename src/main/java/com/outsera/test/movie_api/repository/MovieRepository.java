package com.outsera.test.movie_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outsera.test.movie_api.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT m FROM Movie m WHERE m.winner = true order by m.yearReleased")
    List<Movie> buscarVencedores();
}
