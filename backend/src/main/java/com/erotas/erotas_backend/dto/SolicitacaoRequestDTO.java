package com.erotas.erotas_backend.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitacaoRequestDTO {
    @NotNull
    private Long caronaId;
}
