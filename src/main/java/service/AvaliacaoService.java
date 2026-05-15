package service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.Avaliacao;
import model.Usuario;
import org.springframework.stereotype.Service;
import repository.AvaliacaoRepository;
import repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;

    // RF08 — avaliar motorista (opcional)
    @Transactional
    public Avaliacao avaliar(Long avaliadorId, Long avaliadoId,
                             int nota, String comentario) {
        if (nota < 1 || nota > 5) {
            throw new RuntimeException("Nota deve ser entre 1 e 5");
        }

        Usuario avaliador = usuarioRepository.findById(avaliadorId)
                .orElseThrow(() -> new RuntimeException("Avaliador não encontrado"));
        Usuario avaliado = usuarioRepository.findById(avaliadoId)
                .orElseThrow(() -> new RuntimeException("Avaliado não encontrado"));

        Avaliacao av = new Avaliacao();
        av.setAvaliador(avaliador);
        av.setAvaliado(avaliado);
        av.setNota(nota);
        av.setComentario(comentario);

        return avaliacaoRepository.save(av);
    }

    public Double mediaNotas(Long usuarioId) {
        return avaliacaoRepository.calcularMediaNotas(usuarioId);
    }
}