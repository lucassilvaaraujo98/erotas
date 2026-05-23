package com.erotas.erotas_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// model/Solicitacao.java
@Entity
@Table(name = "solicitacao")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passageiro_id", nullable = false)
    private Usuario passageiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carona_id", nullable = false)
    private Carona carona;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusSolicitacao status = StatusSolicitacao.PENDENTE;

    public void confirmar() {
        this.status = StatusSolicitacao.ACEITA;
        this.carona.setVagasDisponiveis(this.carona.getVagasDisponiveis() - 1);
    }

    public void recusar() {
        this.status = StatusSolicitacao.RECUSADA;
    }
}