package com.project.simple_banking_system.model.DTOs;

public record TransactionRequestDTO(String value, String transactionType, String receiver, String sender) {
    
}
