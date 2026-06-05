package com.erotas.erotas_backend.service;

import com.erotas.erotas_backend.dto.CaronaDTO;
import com.erotas.erotas_backend.model.Carona;
import com.erotas.erotas_backend.model.Motorista;
import com.erotas.erotas_backend.repository.CaronaRepository;
import com.erotas.erotas_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.erotas.erotas_backend.dto.CaronaComSolicitacoesDTO;
import com.erotas.erotas_backend.dto.SolicitacaoResumoDTO;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaronaService {

    private final CaronaRepository caronaRepository;
    private final UsuarioRepository usuarioRepository;

    // RF03 — cadastrar carona
@Transactional
public CaronaDTO cadastrar(Carona carona, Long motoristaId) {
    System.out.println(">>> motoristaId recebido: " + motoristaId);
    
    Motorista motorista = (Motorista) usuarioRepository.findById(motoristaId)
        .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));

    System.out.println(">>> habilitado: " + motorista.isHabilitado());

    if (!motorista.isHabilitado()) {
        throw new RuntimeException("Motorista não está habilitado");
    }

    carona.setMotorista(motorista);
    Carona salva = caronaRepository.save(carona);
    return toDTO(salva);
}

    // RF04 — buscar caronas com filtros
    public List<CaronaDTO> buscar(String origem, String destino) {
        return caronaRepository
                .buscarDisponiveis(origem, destino, LocalDateTime.now())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // RF07 — histórico do motorista
    public List<CaronaDTO> historicoPorMotorista(Long motoristaId) {
        return caronaRepository
                .findByMotoristaIdOrderByDataHoraDesc(motoristaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Carona buscarPorId(Long id) {
        return caronaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carona não encontrada"));
    }

    // Converte entidade → DTO (sem expor motorista completo)
    private CaronaDTO toDTO(Carona c) {
        CaronaDTO dto = new CaronaDTO();
        dto.setId(c.getId());
        dto.setOrigem(c.getOrigem());
        dto.setDestino(c.getDestino());
        dto.setDataHora(c.getDataHora());
        dto.setVagasDisponiveis(c.getVagasDisponiveis());
        dto.setNomeMotorista(c.getMotorista().getNome());
        dto.setDisponivel(c.isDisponivel());
        return dto;
    }

    public List<CaronaComSolicitacoesDTO> buscarComSolicitacoes(Long motoristaId) {
    List<Carona> caronas = caronaRepository
        .findByMotoristaIdOrderByDataHoraDesc(motoristaId);

    return caronas.stream().map(c -> {
        List<SolicitacaoResumoDTO> solic = c.getSolicitacoes().stream()
            .map(s -> new SolicitacaoResumoDTO(
                s.getId(),
                s.getPassageiro().getNome(),
                s.getPassageiro().getEmail(),
                s.getStatus().name()
            )).toList();

        return new CaronaComSolicitacoesDTO(
            c.getId(),
            c.getOrigem(),
            c.getDestino(),
            c.getDataHora(),
            c.getVagasDisponiveis(),
            c.isDisponivel(),
            solic
        );
    }).toList();
}
}