package com.outsera.test.movie_api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.outsera.test.movie_api.dto.PremioIntervaloDTO;
import com.outsera.test.movie_api.entity.Movie;
import com.outsera.test.movie_api.helper.CSVHelper;
import com.outsera.test.movie_api.record.PremioIntervalo;
import com.outsera.test.movie_api.repository.MovieRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    @Value("${csv.file.path}")
    private String filePath;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void loadMoviesFromFile(){
        try{
            logger.info("Iniciando carga de filmes do arquivo CSV: {}", this.filePath);
            movieRepository.saveAll(CSVHelper.loadMoviesFromCSV(this.filePath));
            logger.info("Processo de carga de filmes concluído com sucesso.");
		} catch (Exception e) {
            logger.error("Erro ao carregar filmes do arquivo CSV: ", e);
		}
    }

    /**
     * Calcula os intervalos entre prêmios para cada produtor e retorna os produtores com os menores e maiores intervalos.
     * @return Instância de {@link PremioIntervaloDTO} com lista de produtores com menor e maior intervalo entre prêmios.
     */
    public PremioIntervaloDTO calcularIntervalos() {
        List<Movie> movies = movieRepository.buscarVencedores();

        Map<String, List<PremioIntervalo>> invervaloProdutores = new HashMap<>();

        List<PremioIntervalo> minList = new ArrayList<>();
        List<PremioIntervalo> maxList = new ArrayList<>();

        for (Movie movie : movies) {
            // Faz o split dos produtores considerando vírgulas e " e "
            String[] produtores = movie.getProducers().split(",| and ");

            for (String produtor : produtores) {
                produtor = produtor.trim();
                
                if (produtor.isEmpty()) continue;

                invervaloProdutores.putIfAbsent(produtor, new ArrayList<>());
                List<PremioIntervalo> intervaloEntrePremios = invervaloProdutores.get(produtor);

                if (!intervaloEntrePremios.isEmpty()) {
                    //Busca o último premio do produtor para calcular o intervalo
                    PremioIntervalo last = intervaloEntrePremios.get(intervaloEntrePremios.size() - 1);
                    Integer gap = movie.getYearReleased() - last.followingWin();

                    PremioIntervalo premioIntervalo = new PremioIntervalo(produtor, gap, last.followingWin(), movie.getYearReleased());
                    intervaloEntrePremios.add(premioIntervalo);

                    if (minList.isEmpty() || gap < minList.get(0).interval()) {
                        minList.clear();
                        minList.add(premioIntervalo);
                    } else if (minList.get(0).interval().equals(gap)) {
                        minList.add(premioIntervalo);
                    }

                    if (maxList.isEmpty() || gap > maxList.get(0).interval()) {
                        maxList.clear();
                        maxList.add(premioIntervalo);
                    } else if (maxList.get(0).interval().equals(gap)) {
                        maxList.add(premioIntervalo);
                    }

                } else {
                    intervaloEntrePremios.add(new PremioIntervalo(produtor, 0, movie.getYearReleased(), movie.getYearReleased()));
                }
            }
        }

        return new PremioIntervaloDTO(minList, maxList);
    }
}
