package com.project.simple_banking_system.model.DTOs.Response.statistics;

import com.project.simple_banking_system.model.valueObjects.TransactionType;

import java.math.BigDecimal;

/**
 *
 * @param  type Tipo da transação
 * @param totalTransactions Total de transações de mesmo tipo
 * @param totalMoved Total de dinheiro movimentado por tipo
 * @param  averageMoved Valor médio das transações
 */
public record TransactionDataResponse(TransactionType type, long totalTransactions, BigDecimal totalMoved, Double averageMoved) {
    
}
