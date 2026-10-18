package com.project.simple_banking_system.model.DTOs.Response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

import com.project.simple_banking_system.model.entity.Transaction;

public record TransactionDTO(
                @Schema(description = "ID da transação") UUID transactionID,
                @Schema(description = "Tipo da transação", examples = {
                                "DEPOSITO", "TRANSFERENCIA", "SAQUE" }) String type,
                @Schema(description = "Valor da transação", example = "100.0") String amount,
                @Schema(description = "Destinatário da transação") String destination,
                @Schema(description = "Data de emissão da transação", example = "1970-01-01T00:00:00Z") String emissionDate) {

        public TransactionDTO(Transaction transaction) {
                this(
                        transaction.getId(),
                        transaction.getTransactionType().toString(),
                        transaction.getValue().toString(),
                        transaction.getReceiver(),
                        transaction.getDate().toString()
                );
        }

}
