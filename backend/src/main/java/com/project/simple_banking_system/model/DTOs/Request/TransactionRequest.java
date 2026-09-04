package com.project.simple_banking_system.model.DTOs.Request;

import io.swagger.v3.oas.annotations.media.Schema;

public record TransactionRequest(
        @Schema(description = "Valor da transação", example = "100.00") String value,
        @Schema(description = "Tipo da transação", examples = {"DEPOSITO", "TRANSFERENCIA", "SAQUE"}) String transactionType,
        @Schema(description = "Destinatário da transação", examples = {"EXTERNO", "111222333"}) String receiver,
        @Schema(description = "Remetente da transação", examples = {"EXTERNO", "111222333"}) String sender) {
    
}
