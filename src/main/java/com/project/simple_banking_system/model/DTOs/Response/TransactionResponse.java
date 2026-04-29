package com.project.simple_banking_system.model.DTOs.Response;

public record TransactionResponse(String transactionId, String type, String amount, String newBalance, String destinationAccount, String emissionDate ) {
    
}
