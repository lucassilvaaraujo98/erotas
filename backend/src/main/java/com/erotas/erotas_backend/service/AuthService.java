package com.erotas.erotas_backend.service;

import com.erotas.erotas_backend.config.JwtService;
import com.erotas.erotas_backend.dto.AuthResponse;
import com.erotas.erotas_backend.dto.LoginRequest;
import com.erotas.erotas_backend.dto.RegisterRequest;
import com.erotas.erotas_backend.exception.ApiException;
import com.erotas.erotas_backend.model.Motorista;
import com.erotas.erotas_backend.model.Usuario;
import com.erotas.erotas_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// service/AuthService.java
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

public AuthResponse registrar(RegisterRequest req) {
    if (usuarioRepository.existsByEmail(req.getEmail())) {
        throw new ApiException("E-mail já cadastrado", 409);
    }

    Usuario usuario;

    if (req.isMotorista()) {
        Motorista motorista = new Motorista();
        motorista.setHabilitado(true); // ← habilita automaticamente
        usuario = motorista;
    } else {
        usuario = new Usuario();
    }

    usuario.setNome(req.getNome());
    usuario.setEmail(req.getEmail());
    usuario.setSenha(passwordEncoder.encode(req.getSenha()));
    usuario.setEndereco(req.getEndereco());

    usuarioRepository.save(usuario);
    String token = jwtService.gerarToken(usuario);
    return new AuthResponse(token, usuario.getNome(), usuario.getId());
}

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getSenha())
        );
        Usuario usuario = usuarioRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ApiException("Usuário não encontrado", 404));

        String token = jwtService.gerarToken(usuario);
        return new AuthResponse(token, usuario.getNome(), usuario.getId());
    }
}