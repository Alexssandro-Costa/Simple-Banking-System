package com.project.simple_banking_system.model.DTOs.Request;


import io.swagger.v3.oas.annotations.media.Schema;

public record AccountDataRequest(
        @Schema(description = "Token de autenticação do usuário") String token) {
}
