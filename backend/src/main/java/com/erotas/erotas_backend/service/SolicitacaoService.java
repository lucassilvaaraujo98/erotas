package com.erotas.erotas_backend.service;

import com.erotas.erotas_backend.exception.ApiException;
import com.erotas.erotas_backend.model.Carona;
import com.erotas.erotas_backend.model.Solicitacao;
import com.erotas.erotas_backend.model.StatusSolicitacao;
import com.erotas.erotas_backend.model.Usuario;
import com.erotas.erotas_backend.repository.CaronaRepository;
import com.erotas.erotas_backend.repository.SolicitacaoRepository;
import com.erotas.erotas_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// service/SolicitacaoService.java
@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final CaronaRepository caronaRepository;
    private final UsuarioRepository usuarioRepository;

    // RF05 — passageiro solicita carona
    @Transactional
    public Solicitacao solicitar(Long passageiroId, Long caronaId) {
        if (solicitacaoRepository.existsByPassageiroIdAndCaronaId(passageiroId, caronaId)) {
            throw new RuntimeException("Você já solicitou esta carona");
        }

        Carona carona = caronaRepository.findById(caronaId)
                .orElseThrow(() -> new RuntimeException("Carona não encontrada"));

        if (!carona.isDisponivel()) {
            throw new RuntimeException("Carona sem vagas ou já encerrada");
        }

        Usuario passageiro = usuarioRepository.findById(passageiroId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Solicitacao s = new Solicitacao();
        s.setPassageiro(passageiro);
        s.setCarona(carona);
        s.setStatus(StatusSolicitacao.PENDENTE);

        return solicitacaoRepository.save(s);
    }

    // RF06 — motorista aceita
    @Transactional
    public Solicitacao confirmar(Long solicitacaoId, Long motoristaId) {
        Solicitacao s = buscarPorId(solicitacaoId);
        validarMotorista(s, motoristaId);
        s.confirmar(); // desconta vaga e muda status
        return solicitacaoRepository.save(s);
    }

    // RF06 — motorista recusa
    @Transactional
    public Solicitacao recusar(Long solicitacaoId, Long motoristaId) {
        Solicitacao s = buscarPorId(solicitacaoId);
        validarMotorista(s, motoristaId);
        s.recusar();
        return solicitacaoRepository.save(s);
    }

    // RF07 — histórico do passageiro
    public List<Solicitacao> historicoPorPassageiro(Long passageiroId) {
        return solicitacaoRepository.findByPassageiroIdOrderByIdDesc(passageiroId);
    }

    private Solicitacao buscarPorId(Long id) {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
    }

    // Todos podem ver solicitações pendentes
    public List<Solicitacao> listarTodas() {
        return solicitacaoRepository.findByStatus(StatusSolicitacao.PENDENTE);
    }

    public List<Solicitacao> listarPorCarona(Long caronaId) {
        caronaRepository.findById(caronaId)
                .orElseThrow(() -> new ApiException("Carona não encontrada", 404));

        return solicitacaoRepository.findByCaronaId(caronaId);
    }

    private void validarMotorista(Solicitacao s, Long motoristaId) {
        if (!s.getCarona().getMotorista().getId().equals(motoristaId)) {
            throw new RuntimeException("Você não é o motorista desta carona");
        }
    }
}
