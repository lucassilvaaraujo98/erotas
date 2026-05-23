package com.erotas.erotas_backend.model;

import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "motorista")
@DiscriminatorValue("MOTORISTA")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Motorista extends Usuario {

    @Column(nullable = false)
    private boolean habilitado = false;

    public void aceitarSolicitacao(Solicitacao solicitacao) {
        solicitacao.confirmar();
    }
}