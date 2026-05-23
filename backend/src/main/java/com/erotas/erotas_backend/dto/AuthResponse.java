package com.erotas.erotas_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// dto/AuthResponse.java
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String nome;
    private Long id;
}
