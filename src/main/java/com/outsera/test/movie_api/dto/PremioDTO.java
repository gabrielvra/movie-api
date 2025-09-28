package com.outsera.test.movie_api.dto;

import lombok.Data;

@Data
public class PremioDTO {
    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}
