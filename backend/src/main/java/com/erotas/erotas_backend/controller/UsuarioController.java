package com.erotas.erotas_backend.controller;


import com.erotas.erotas_backend.exception.ApiException;
import com.erotas.erotas_backend.model.Motorista;
import com.erotas.erotas_backend.model.Usuario;
import com.erotas.erotas_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @PutMapping("/{id}/habilitar")
    public ResponseEntity<String> habilitarMotorista(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ApiException("Usuário não encontrado", 404));

        if (!(usuario instanceof Motorista motorista)) {
            throw new ApiException("Usuário não é um motorista", 400);
        }

        motorista.setHabilitado(true);
        usuarioRepository.save(motorista);
        return ResponseEntity.ok("Motorista habilitado com sucesso");
    }
}