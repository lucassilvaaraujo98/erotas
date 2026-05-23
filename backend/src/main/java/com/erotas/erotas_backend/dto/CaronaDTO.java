package com.erotas.erotas_backend.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaronaDTO {
    private Long id;
    private String origem;
    private String destino;
    private LocalDateTime dataHora;
    private int vagasDisponiveis;
    private String nomeMotorista;
    private boolean disponivel;
}
