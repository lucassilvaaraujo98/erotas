package com.erotas.erotas_backend.controller;


import com.erotas.erotas_backend.config.JwtService;
import com.erotas.erotas_backend.dto.CaronaComSolicitacoesDTO;
import com.erotas.erotas_backend.dto.CaronaDTO;
import com.erotas.erotas_backend.model.Carona;
import com.erotas.erotas_backend.service.CaronaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caronas")
@RequiredArgsConstructor
public class CaronaController {

    private final CaronaService caronaService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<CaronaDTO>> buscar(
            @RequestParam(required = false) String origem,
            @RequestParam(required = false) String destino) {
        return ResponseEntity.ok(caronaService.buscar(origem, destino));
    }

    @PostMapping
    public ResponseEntity<CaronaDTO> cadastrar(
            @RequestBody @Valid Carona carona,
            @RequestHeader("Authorization") String authHeader) {
        Long motoristaId = extrairId(authHeader);
        return ResponseEntity.status(201).body(caronaService.cadastrar(carona, motoristaId));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<CaronaDTO>> historico(
            @RequestHeader("Authorization") String authHeader) {
        Long id = extrairId(authHeader);
        return ResponseEntity.ok(caronaService.historicoPorMotorista(id));
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<CaronaComSolicitacoesDTO>> minhasCaronas(
            @RequestHeader("Authorization") String authHeader) {
        Long motoristaId = extrairId(authHeader);
        return ResponseEntity.ok(caronaService.buscarComSolicitacoes(motoristaId));
}

    private Long extrairId(String authHeader) {
        return jwtService.extrairId(authHeader.substring(7));
    }
}
