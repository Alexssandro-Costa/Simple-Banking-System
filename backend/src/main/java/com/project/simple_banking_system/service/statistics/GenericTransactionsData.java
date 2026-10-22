package com.project.simple_banking_system.service.statistics;

import com.project.simple_banking_system.exceptions.statistics.StatisticNotFoundException;
import com.project.simple_banking_system.model.DTOs.Response.statistics.TransactionDataResponse;
import com.project.simple_banking_system.service.auth.TokenData;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.simple_banking_system.model.DTOs.Response.statistics.GenericDataResponse;
import com.project.simple_banking_system.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Responsável pelas informações genéricas de transações bancarias relacionadas a uma conta bancaria
 * @version 1.0
 */
@Service
public class GenericTransactionsData {

    @Autowired 
    private TransactionRepository transactionRepository;

    @Autowired
    private TokenData tokenData;


    public GenericDataResponse get() {

        try {
            // recupera os dados estatísticos do banco de dados
            List<TransactionDataResponse> transactionDataResponseList = transactionRepository
                    .getGenericTransactionsStatisticsByAccountId(tokenData.getAccountId())
                    .orElseThrow();

            return new GenericDataResponse(transactionDataResponseList);

        } catch (NoSuchElementException e) {
            throw new StatisticNotFoundException(e.getMessage());
        }
    }

    
}
