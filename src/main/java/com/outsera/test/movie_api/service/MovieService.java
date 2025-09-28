package com.outsera.test.movie_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.outsera.test.movie_api.helper.CSVHelper;
import com.outsera.test.movie_api.repository.MovieRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Value("${csv.file.path}")
    private String filePath;

    @PostConstruct
    public void loadMoviesFromFile(){
        try{
            movieRepository.saveAll(CSVHelper.loadMoviesFromCSV(this.filePath));
		} catch (Exception e) {
			System.out.println("Erro ao carregar filmes do arquivo CSV: " + e.getMessage());
		}
    }

    
}
