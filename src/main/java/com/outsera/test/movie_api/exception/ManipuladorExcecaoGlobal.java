package com.outsera.test.movie_api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.outsera.test.movie_api.record.RespostaErro;

@RestControllerAdvice
public class ManipuladorExcecaoGlobal {

    private static final Logger logger = LoggerFactory.getLogger(ManipuladorExcecaoGlobal.class);
    
    @ExceptionHandler(ErroProcessamento.class)
    public ResponseEntity<RespostaErro> manipularErroProcessamento(ErroProcessamento ex) {
        logger.error("Erro não esperado identificado.", ex);
        RespostaErro respostaErro = new RespostaErro(
            "Erro interno do servidor",
            ex.getMessage()
        );
        return new ResponseEntity<>(respostaErro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
