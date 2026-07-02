package com.project.simple_banking_system.service.UseCases;

import java.math.BigDecimal;

import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.repository.AccountRepository;
import com.project.simple_banking_system.repository.TransactionRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Valida e realiza um saque no saldo de uma conta bancaria
 * @author Alexssandro
 * @since release 3
 * @version 1.1
 */
@Service
@Transactional
public class PerformDeposit {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Execute a operação de deposito.
     * @param account Conta bancaria relacionada.
     * @param transaction Transcação que deve ser realizada.
     */
    @Transactional
    protected TransactionResponse execute(@NonNull Account account, @NonNull Transaction transaction) {

            // valida os dados passados
            validate(account, transaction.getValue());

            // realiza a operação
            Cash balance = account.getBalance();
            balance.add(transaction.getValue());
            account.setBalance(balance);

            // registra a transação no histórico da conta
            account.getTransactions().add(transaction);
            transaction.setAccount(account);

            // salva a operação no banco
            transactionRepository.save(transaction);

            // retorna um dto de resposta
            return new TransactionResponse(
                    String.valueOf(transaction.getId()),
                    transaction.getTransactionType().name(),
                    transaction.getValue().toString(),
                    transaction.getAccount().getBalance().toString(),
                    transaction.getReceiver(),
                    transaction.getDate().toString()
            );


    }

    
    /**
     * Verifica se a operação é valida.
     * @param account Conta bancaria associada.
     * @param depositValue - Valor de deposito
     * @exception NullElementException Lançada quando um elemento é nulo.
     * @exception InvalidTransactionException Lançada quando uma operação não é possivel.
     */
    private void validate(Account account, Cash depositValue) {

        // conta bancaria é nula
        if(account == null)
            throw new NullElementException("ERRO! Conta bancaria é invalida.");

        // saldo é nulo
        if(account.getBalance() == null || account.getBalance().getValue() == null)
            throw new NullElementException("Saldo bancario é invalido.");

        // valor de deposito é nulo
        if(depositValue == null || depositValue.getValue() == null) 
            throw new NullElementException("Valor de deposito é invalido.");

        // valor de depósito é negativo
        if(depositValue.getValue().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidTransactionException("Valor de deposito não pode ser negativo.");

    }
    
}
