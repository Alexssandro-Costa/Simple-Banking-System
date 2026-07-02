package com.project.simple_banking_system.model.DTOs.Response;

import com.project.simple_banking_system.model.entity.Transaction;

public record TransactionResponse(String transactionId, String type, String amount, String newBalance, String destination, String emissionDate ) {

}
