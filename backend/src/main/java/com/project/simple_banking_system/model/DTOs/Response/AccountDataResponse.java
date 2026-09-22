package com.project.simple_banking_system.model.DTOs.Response;
import io.swagger.v3.oas.annotations.media.Schema;

public record AccountDataResponse(
        @Schema(description = "Nome do usuário", example = "ALEX DA COSTA") String name,
        @Schema(description = "Número da conta bancária", example = "012345678") String accountNumber,
        @Schema(description = "Saldo da conta", example = "1000.00") String balance) {
    
}
