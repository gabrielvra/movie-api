package com.outsera.test.movie_api.record;

public record RespostaErro(String mensagem, String detalhes, long timestamp) {
    public RespostaErro(String mensagem, String detalhes) {
        this(mensagem, detalhes, System.currentTimeMillis());
    }
}