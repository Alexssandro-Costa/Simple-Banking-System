package com.project.simple_banking_system.service.caseUses;

import java.math.BigDecimal;

import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.valueObjects.Cash;


/**
 * Valida e realiza um saque no saldo de uma conta bancaria
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
public class PerformDeposit {


    /**
     * Execute a operação de deposito.
     * @param account Conta bancaria relacionada.
     * @param depositValue Valor do deposito.
     */
    protected static void execute(Account account, Cash depositValue) {

        validate(account, depositValue);

        Cash balance = account.getBalance();

        // atualiza o valor do saldo
        balance.setValue( balance.getValue().add(depositValue.getValue()) );

        account.setBalance(balance);
    }

    
    /**
     * Verifica se a operação é valida.
     * @param account Conta bancaria associada.
     * @param depositValue - Valor de deposito
     * @exception NullElementException Lançada quando um elemento é nulo.
     * @exception InvalidTransactionException Lançada quando uma operação não é possivel.
     */
    private static void validate(Account account, Cash depositValue) {

        
        // conta bancaria é nula
        if(account == null)
            throw new NullElementException("ERRO! Conta bancaria é invalida.");

        // saldo é nulo
        if(account.getBalance() == null || account.getBalance().getValue() == null)
            throw new NullElementException("Saldo bancario é invalido.");

        // valor de deposito é nulo
        if(depositValue == null || depositValue.getValue() == null) 
            throw new NullElementException("Valor de deposito é invalido.");

        // valor de deposito é negativo
        if(depositValue.getValue().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidTransactionException("Valor de deposito não pode ser negativo.");

    }
    
}
