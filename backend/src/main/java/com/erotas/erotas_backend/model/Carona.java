package com.erotas.erotas_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorista_id", nullable = false)
    @JsonIgnoreProperties({"caronas", "senha", "solicitacoes"})
    private Motorista motorista;

    @OneToMany(mappedBy = "carona", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Solicitacao> solicitacoes = new ArrayList<>();

    public boolean isDisponivel() {
        return vagasDisponiveis > 0 && dataHora.isAfter(LocalDateTime.now());
    }
}