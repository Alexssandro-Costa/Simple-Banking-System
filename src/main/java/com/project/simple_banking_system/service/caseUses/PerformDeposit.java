package com.project.simple_banking_system.service.caseUses;

import com.project.simple_banking_system.model.valueObjects.Cash;

public class PerformDeposit {

    protected static Cash execute(Cash balance, Cash depositValue) {


        // atualiza o valor do balanço
        balance.setValue( balance.getValue().add(depositValue.getValue()) );

        return balance;
    }
    
}
