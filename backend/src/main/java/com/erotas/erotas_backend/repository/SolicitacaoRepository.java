package com.erotas.erotas_backend.repository;

import com.erotas.erotas_backend.model.Solicitacao;
import com.erotas.erotas_backend.model.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    // Todas as solicitações pendentes do sistema
    List<Solicitacao> findByStatus(StatusSolicitacao status);

    // Histórico de solicitações do passageiro (RF07)
    List<Solicitacao> findByPassageiroIdOrderByIdDesc(Long passageiroId);

    // Todas as solicitações de uma carona (para o motorista ver)
    List<Solicitacao> findByCaronaId(Long caronaId);

    // Verifica se o usuário já solicitou essa carona
    boolean existsByPassageiroIdAndCaronaId(Long passageiroId, Long caronaId);
}