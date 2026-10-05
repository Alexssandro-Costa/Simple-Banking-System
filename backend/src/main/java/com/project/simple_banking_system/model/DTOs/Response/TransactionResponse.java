package com.project.simple_banking_system.model.DTOs.Response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record TransactionResponse(
        @Schema(description = "ID da transação") UUID transactionId,
        @Schema(description = "Tipo da transação", examples = {"DEPOSITO", "TRANSFERENCIA", "SAQUE"}) String type,
        @Schema(description = "Valor da transação", example = "100.0") String amount,
        @Schema(description = "Novo saldo da conta", example = "1000.00")String newBalance,
        @Schema(description = "Destinatário da transação") String destination,
        @Schema(description = "Data de emissão da transação", example = "1970-01-01T00:00:00Z") String emissionDate ) {

}
