package com.outsera.test.movie_api.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.outsera.test.movie_api.model.Movie;

public final class CSVHelper {

    private CSVHelper() {
    }  

    public static List<Movie> loadMoviesFromCSV(String filePath) throws IOException {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
            //Pula a primeira linha do header do CSV
            if (csvReader.readLine() != null){
                List<Movie> movies = new ArrayList<>();
                String linha;
                while ((linha = csvReader.readLine()) != null) {
                    try {
                        String[] values = linha.split(";", -1);
                        if (values.length < 5) {
                            throw new IllegalArgumentException("Número incorreto de colunas");
                        }
                        Integer year = Integer.parseInt(values[0].trim());                
                        String title = values[1].trim();
                        String studios = values[2].trim();
                        String producers = values[3].trim();
                        Boolean winner = values[4].trim().equals("yes");

                        Movie movie = new Movie();
                        movie.setYearReleased(year);
                        movie.setTitle(title);
                        movie.setStudios(studios);
                        movie.setProducers(producers);
                        movie.setWinner(winner);

                        movies.add(movie);
                    } catch (Exception e) {
                        System.out.println("Registro inválido: "+linha+" - pulando... {"+e.getMessage()+"}");
                    }
                }          
                return movies;  
            }
        }
        return Collections.emptyList();
    }
}
