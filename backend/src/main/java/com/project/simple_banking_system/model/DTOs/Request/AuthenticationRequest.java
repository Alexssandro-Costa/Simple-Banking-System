package com.project.simple_banking_system.model.DTOs.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(
        @Schema(description = "CPF do usuário", example = "000.000.000-00") String cpf,
        @Schema(description = "Senha do usuário", example = "Alex2366") String password) {
    
}
