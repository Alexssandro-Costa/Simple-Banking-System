package com.project.simple_banking_system.service.caseUses;

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

        Cash balance = account.getBalance();

        // atualiza o saldo
        balance.setValue(balance.getValue().subtract(withdrawValue.getValue()));

        account.setBalance(balance);
    }
    
}
