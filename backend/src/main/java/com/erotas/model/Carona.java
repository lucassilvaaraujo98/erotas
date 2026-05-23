package com.erotas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// model/Carona.java
@Entity
@Table(name = "carona")
@Data
@Setter
@Getter
@NoArgsConstructor
public class Carona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String origem;

    @Column(nullable = false, length = 100)
    private String destino;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private int vagasDisponiveis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @OneToMany(mappedBy = "carona", cascade = CascadeType.ALL)
    private List<Solicitacao> solicitacoes = new ArrayList<>();

    public boolean isDisponivel() {
        return vagasDisponiveis > 0 && dataHora.isAfter(LocalDateTime.now());
    }
}