package com.project.simple_banking_system.model.DTOs.Response;


import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;

import java.time.Instant;
import java.util.UUID;

public record CheckStatementResponse(UUID transactionID, TransactionType type, Cash amount, String destinationAccount, Instant emissionDate) {

    
}
