package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "motorista")
@DiscriminatorValue("MOTORISTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Motorista extends Usuario {

    @Column(nullabe = false)
    private boolean habilitado = false;

    public void aceitarSolicitacao(Solicitacao solicitacao){
        solicitacao.confirmar();
    }

}
