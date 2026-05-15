package service;


import config.JwtService;
import dto.AuthResponse;
import dto.LoginRequest;
import dto.RegisterRequest;
import exception.ApiException;
import lombok.RequiredArgsConstructor;
import model.Motorista;
import model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UsuarioRepository;

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

        Usuario usuario = req.isMotorista() ? new Motorista() : new Usuario();
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