package com.outsera.test.movie_api.dto;

import java.util.List;

import lombok.Data;

@Data
public class IntervaloPremioDTO {
    private List<PremioDTO> min;
    private List<PremioDTO> max;
}
