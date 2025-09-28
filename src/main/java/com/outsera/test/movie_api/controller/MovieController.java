package com.outsera.test.movie_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsera.test.movie_api.dto.PremioIntervaloDTO;
import com.outsera.test.movie_api.exception.ErroProcessamento;
import com.outsera.test.movie_api.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/intervalos-premio")
    public ResponseEntity<PremioIntervaloDTO> getIntervaloPremio() {
        try {
            PremioIntervaloDTO resposta = movieService.calcularIntervalos();
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            throw new ErroProcessamento("Erro ao processar a requisição: " + e.getMessage());
        }
    }
}
