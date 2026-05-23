package com.erotas.erotas_backend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank private String email;
    @NotBlank private String senha;
}
