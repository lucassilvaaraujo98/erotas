package repository;

import model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// repository/SolicitacaoRepository.java
@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    // Histórico de solicitações do passageiro (RF07)
    List<Solicitacao> findByPassageiroIdOrderByIdDesc(Long passageiroId);

    // Todas as solicitações de uma carona (para o motorista ver)
    List<Solicitacao> findByCaronaId(Long caronaId);

    // Verifica se o usuário já solicitou essa carona
    boolean existsByPassageiroIdAndCaronaId(Long passageiroId, Long caronaId);
}