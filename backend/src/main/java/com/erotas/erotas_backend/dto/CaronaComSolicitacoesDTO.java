package com.erotas.erotas_backend.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaronaComSolicitacoesDTO {
    private Long id;
    private String origem;
    private String destino;
    private LocalDateTime dataHora;
    private Integer vagasDisponiveis;
    private Boolean disponivel;
    private List<SolicitacaoResumoDTO> solicitacoes;
}