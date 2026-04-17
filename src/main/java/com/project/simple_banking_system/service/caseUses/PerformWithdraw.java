package com.project.simple_banking_system.service.caseUses;

import com.project.simple_banking_system.model.valueObjects.Cash;

public class PerformWithdraw {


    protected static Cash execute(Cash balance, Cash withdrawValue) {
        
        balance.setValue(balance.getValue().subtract(withdrawValue.getValue()));

        return balance;
    }
    
}
