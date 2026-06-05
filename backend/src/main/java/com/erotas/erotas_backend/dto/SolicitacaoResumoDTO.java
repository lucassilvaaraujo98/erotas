package com.erotas.erotas_backend.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoResumoDTO {
    private Long id;
    private String nomePassageiro;
    private String emailPassageiro;
    private String status;
}