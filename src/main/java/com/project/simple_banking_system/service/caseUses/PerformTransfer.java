package com.project.simple_banking_system.service.caseUses;

import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;


/**
 * Valida e realiza uma transferencia entre duas contas bancarias.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
public class PerformTransfer {

    
    /**
     * Executa a operação de transferência.
     * @param receiver Conta Destinataria do valor.
     * @param sender Conta Remetente do valor.
     * @param transaction Transação que está sendo realizada.
     */
    public static void execute(Account receiver, Account sender, Transaction transaction) {
        PerformWithdraw.execute(sender, transaction.getValue());
        PerformDeposit.execute(receiver, transaction.getValue());
    }
}
