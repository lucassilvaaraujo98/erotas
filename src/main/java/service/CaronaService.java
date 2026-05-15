package service;

import jakarta.transaction.Transactional;
import model.Carona;
import model.Motorista;
import repository.CaronaRepository;
import repository.UsuarioRepository;

import dto.CaronaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Motorista motorista = (Motorista) usuarioRepository.findById(motoristaId)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));

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
}