package com.project.simple_banking_system.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simple_banking_system.model.DTOs.AccountDTO;
import com.project.simple_banking_system.model.DTOs.LoginRequestDTO;
import com.project.simple_banking_system.model.DTOs.RegisterRequestDTO;
import com.project.simple_banking_system.model.DTOs.TransactionRequestDTO;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.service.caseUses.AccessAccount;
import com.project.simple_banking_system.service.caseUses.PerformTransaction;
import com.project.simple_banking_system.service.caseUses.RegisterNewClient;

@RequestMapping("/banco")
@RestController
public class BankingSystemController {

    @Autowired
    private RegisterNewClient registerNewClient;

    @Autowired
    private AccessAccount accessAccount;

    @Autowired
    private PerformTransaction performTransaction;
    
    @PostMapping("/register")
    public AccountDTO registerNewClient(@RequestBody RegisterRequestDTO registerRequest) {
        return registerNewClient.execute(registerRequest);
    }

    @GetMapping("/login")
    public AccountDTO AccessAccount(@RequestBody LoginRequestDTO loginRequest) {
        return accessAccount.execute(loginRequest);
    }

    @PostMapping("/{accountNumber}/transaction")
    public void performTransaction(@PathVariable String accountNumber, @RequestBody TransactionRequestDTO transaction) {

        performTransaction.execute(accountNumber, transaction );        
    }


    
}
