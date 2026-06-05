package com.erotas.erotas_backend.dto;


import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaronaDTO {
    private Long id;
    private String origem;
    private String destino;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;
    private int vagasDisponiveis;
    private String nomeMotorista;
    private boolean disponivel;
}
