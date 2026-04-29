package com.project.simple_banking_system.model.DTOs.Response;


public record CheckStatementResponse(String transcationID, String type, String amount, String destinationAccount, String emissionDate) {

    
}
