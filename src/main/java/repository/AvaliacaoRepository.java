package repository;

import model.Avaliacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// repository/AvaliacaoRepository.java
@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByAvaliadoId(Long avaliadoId);

    // Média de notas de um usuário
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.avaliado.id = :id")
    Double calcularMediaNotas(@Param("id") Long id);
}