package com.project.simple_banking_system.model.DTOs.Request;

public record TransactionRequest(String value, String transactionType, String receiver, String sender) {
    
}
