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
public class PerformWithdraw {

    /**
     * Executa a operação de saque.
     * @param account Conta bancaria relacionada.
     * @param withdrawValue Valor do saque 
     */
    protected static void execute(Account account, Cash withdrawValue) {

        validate(account, withdrawValue);

        Cash balance = account.getBalance();
        // atualiza o saldo
        balance.setValue(balance.getValue().subtract(withdrawValue.getValue()));

        account.setBalance(balance);
    }


    /**
     * Verifica se a operação é valida.
     * @param account Conta bancaria associada.
     * @param withdrawValue - Valor de saque
     * @exception NullElementException Lançada quando um elemento é nulo.
     * @exception InvalidTransactionException Lançada quando uma operação não é possivel.
     */
    private static void validate(Account account, Cash withdrawValue) {

        // conta bancaria é nula
        if(account == null)
            throw new NullElementException("ERRO! Conta bancaria é invalida.");

        // saldo é nulo
        if(account.getBalance() == null || account.getBalance().getValue() == null)
            throw new NullElementException("Saldo bancario é invalido.");

        // valor de saque é nulo
        if(withdrawValue == null || withdrawValue.getValue() == null)
            throw new NullElementException("Valor de saque é invalido.");

        // valor de saque é negativo
        if(withdrawValue.getValue().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidTransactionException("O valor de saque não pode ser negativo."); 

        // valor de saque é maior que o saldo
        if(account.getBalance().getValue().compareTo(withdrawValue.getValue()) < 0)
            throw new InvalidTransactionException("Valor de saque não pode ser maior que o saldo.");

    }
    
}
