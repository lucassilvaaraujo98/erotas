package repository;

import model.Carona;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CaronaRepository extends JpaRepository<Carona, Long> {

    //Busca caronas disponíveis com filtros (RF04)
    List<Carona> findByOrigemContaingIgnoreCaseAndDestinoContaingIngnoreCase(
            String origem, String destino
    );

    // Histórico de caronas de um motorista (RF07)
    List<Carona> findByMotoristaIdOrderByDataHoraDesc(Long motoristaId);

    // Busca por data (caronas futuras)
    List<Carona> findByDataHoraAfterOrderByDataHora(LocalDateTime agora);

    // Busca com filtro completo via JPQL
    @Query("""
        SELECT c FROM Carona c
        WHERE (:origem IS NULL OR LOWER(c.origem) LIKE LOWER(CONCAT('%', :origem, '%')))
        AND   (:destino IS NULL OR LOWER(c.destino) LIKE LOWER(CONCAT('%', :destino, '%')))
        AND   c.dataHora > :agora
        AND   c.vagasDisponiveis > 0
        ORDER BY c.dataHora ASC
    """)
    List<Carona> buscarDisponiveis(
            @Param("origem")  String origem,
            @Param("destino") String destino,
            @Param("agora") LocalDateTime agora
    );
}
