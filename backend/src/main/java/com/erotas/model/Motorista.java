package com.erotas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;


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