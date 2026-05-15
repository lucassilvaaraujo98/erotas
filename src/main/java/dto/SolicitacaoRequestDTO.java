package dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitacaoRequestDTO {
    @NotNull
    private Long caronaId;
}
