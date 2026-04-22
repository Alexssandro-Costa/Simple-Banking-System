package com.project.simple_banking_system.model.DTOs;

public record TranscationResponseDTO(String transcationID, String type, String amount, String newBalance, String destinationAccount, String emissionDate ) {
    
}
