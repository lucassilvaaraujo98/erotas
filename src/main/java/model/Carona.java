package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carona")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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