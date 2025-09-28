package com.outsera.test.movie_api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.outsera.test.movie_api.record.RespostaErro;

@RestControllerAdvice
public class ManipuladorExcecaoGlobal {

    private static final Logger logger = LoggerFactory.getLogger(ManipuladorExcecaoGlobal.class);
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> manipularExcecaoGeral(Exception ex) {
        logger.error("Erro não esperado identificado.", ex);
        RespostaErro respostaErro = new RespostaErro(
            "Erro interno do servidor",
            "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde."
        );
        return new ResponseEntity<>(respostaErro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
