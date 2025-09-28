package com.outsera.test.movie_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsera.test.movie_api.dto.IntervaloPremioDTO;
import com.outsera.test.movie_api.model.Movie;
import com.outsera.test.movie_api.repository.MovieRepository;
import com.outsera.test.movie_api.service.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping()
    public IntervaloPremioDTO getIntervaloPremio() {
        return new IntervaloPremioDTO();
    }
}
