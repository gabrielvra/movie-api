package com.outsera.test.movie_api.exception;

public class ErroProcessamento extends RuntimeException {
    public ErroProcessamento(String mensagem) {
        super(mensagem);
    }
}
