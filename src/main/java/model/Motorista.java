package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name= "motorista")
@DiscriminatorValue("MOTORISTA")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Motorista extends Usuario {

    @Column(nullable = false)
    private boolean habilitado = false;

    public void aceitarSolicitacao(Solicitacao solicitacao){
        solicitacao.confirmar();
    }

}
