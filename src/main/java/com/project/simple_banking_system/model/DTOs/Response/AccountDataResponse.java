package com.project.simple_banking_system.model.DTOs.Response;

import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.Name;
import io.swagger.v3.oas.annotations.media.Schema;

public record AccountDataResponse(
        @Schema(description = "Nome do usuário", example = "ALEX DA COSTA") Name name,
        @Schema(description = "Número da conta bancária", example = "012345678") AccountNumber accountNumber,
        @Schema(description = "Saldo da conta", example = "1000.00") Cash balance) {
    
}
