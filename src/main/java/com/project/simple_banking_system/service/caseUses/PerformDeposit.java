package com.project.simple_banking_system.service.caseUses;

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

        Cash balance = account.getBalance();

        // atualiza o valor do saldo
        balance.setValue( balance.getValue().add(depositValue.getValue()) );

        account.setBalance(balance);
    }
    
}
