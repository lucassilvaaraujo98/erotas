package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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