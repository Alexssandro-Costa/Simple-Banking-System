package com.project.simple_banking_system.model.DTOs.Response.statistics;

import java.util.List;

/**
 * Mantêm dados estatísticos genéricos sobre os tipos de transações
 * @param dataResponse Lista de Dados relacionados a cada tipo de transação
 */
public record GenericDataResponse(List<TransactionDataResponse> dataResponse) {
    
}
