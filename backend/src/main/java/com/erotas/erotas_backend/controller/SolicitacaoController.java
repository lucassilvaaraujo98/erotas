package com.erotas.erotas_backend.controller;

import com.erotas.erotas_backend.config.JwtService;
import com.erotas.erotas_backend.dto.SolicitacaoRequestDTO;
import com.erotas.erotas_backend.model.Solicitacao;
import com.erotas.erotas_backend.service.SolicitacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller/SolicitacaoController.java
@RestController
@RequestMapping("/api/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Solicitacao>> listarTodas() {
        return ResponseEntity.ok(solicitacaoService.listarTodas());
    }

    @GetMapping("/carona/{caronaId}")
    public ResponseEntity<List<Solicitacao>> listarPorCarona(
            @PathVariable Long caronaId) {
        return ResponseEntity.ok(solicitacaoService.listarPorCarona(caronaId));
    }


    @PostMapping
    public ResponseEntity<Solicitacao> solicitar(
            @RequestBody @Valid SolicitacaoRequestDTO dto,
            @RequestHeader("Authorization") String authHeader) {
        Long passageiroId = extrairId(authHeader);
        return ResponseEntity.status(201)
                .body(solicitacaoService.solicitar(passageiroId, dto.getCaronaId()));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Solicitacao> confirmar(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        Long motoristaId = extrairId(authHeader);
        return ResponseEntity.ok(solicitacaoService.confirmar(id, motoristaId));
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<Solicitacao> recusar(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        Long motoristaId = extrairId(authHeader);
        return ResponseEntity.ok(solicitacaoService.recusar(id, motoristaId));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<Solicitacao>> historico(
            @RequestHeader("Authorization") String authHeader) {
        Long passageiroId = extrairId(authHeader);
        return ResponseEntity.ok(solicitacaoService.historicoPorPassageiro(passageiroId));
    }

    private Long extrairId(String authHeader) {
        return jwtService.extrairId(authHeader.substring(7));
    }
}