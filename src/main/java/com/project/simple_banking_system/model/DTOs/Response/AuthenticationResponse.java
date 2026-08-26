package com.project.simple_banking_system.model.DTOs.Response;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationResponse(
        @Schema(description = "Token de acesso do usuário") String token) {
    
}
