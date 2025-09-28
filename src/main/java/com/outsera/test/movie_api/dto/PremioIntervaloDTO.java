package com.outsera.test.movie_api.dto;

import java.util.List;

import com.outsera.test.movie_api.record.PremioIntervalo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PremioIntervaloDTO {
    private List<PremioIntervalo> min;
    private List<PremioIntervalo> max;
}
