package com.project.simple_banking_system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DTOs 
import com.project.simple_banking_system.model.DTOs.ChangeStatusRequestDTO;
import com.project.simple_banking_system.model.DTOs.LoginRequestDTO;
import com.project.simple_banking_system.model.DTOs.RegisterRequestDTO;
import com.project.simple_banking_system.model.DTOs.TransactionRequestDTO;

// classes de serviço
import com.project.simple_banking_system.service.caseUses.AccessAccount;
import com.project.simple_banking_system.service.caseUses.ChangeAccountStatus;
import com.project.simple_banking_system.service.caseUses.CheckStatement;
import com.project.simple_banking_system.service.caseUses.PerformTransaction;
import com.project.simple_banking_system.service.caseUses.RegisterNewClient;

import jakarta.annotation.Nonnull;


/**
 * Controller do sistema que centraliza as requisições.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@RequestMapping("/banco")
@RestController
public class BankingSystemController {

    @Autowired
    private RegisterNewClient registerNewClient;

    @Autowired
    private AccessAccount accessAccount;

    @Autowired
    private PerformTransaction performTransaction;

    @Autowired
    private ChangeAccountStatus changeAccountStatus;

    @Autowired
    private CheckStatement checkStatement;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerNewClient(@Nonnull @RequestBody RegisterRequestDTO registerRequest) {
        var result = registerNewClient.execute(registerRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> AccessAccount(@Nonnull @RequestBody LoginRequestDTO loginRequest) {
        var result = accessAccount.execute(loginRequest);
        return ResponseEntity.ok(result);
        
    }

    @PostMapping("/account/{accountNumber}/transaction")
    public ResponseEntity<?> performTransaction(@Nonnull @PathVariable String accountNumber, @Nonnull @RequestBody TransactionRequestDTO transactionRequest) {

        var result = performTransaction.execute(accountNumber, transactionRequest);
        return ResponseEntity.ok(result);   
    }

    @PatchMapping("/account/{accountNumber}/disable-account")
    public void disableAccount(@Nonnull @PathVariable String accountNumber, @Nonnull @RequestBody ChangeStatusRequestDTO changeStatusRequestDTO) {
        changeAccountStatus.execute(accountNumber, changeStatusRequestDTO);

    }

    @PatchMapping("/account/{accountNumber}/enable-account")
    public void enableAccount(@Nonnull @PathVariable String accountNumber, @Nonnull @RequestBody ChangeStatusRequestDTO changeStatusRequestDTO) {

        changeAccountStatus.execute(accountNumber, changeStatusRequestDTO);

    }

    @GetMapping("/account/{accountNumber}/statement")
    public ResponseEntity<?> checkStatement(@Nonnull @PathVariable String accountNumber) {

        var result = checkStatement.execute(accountNumber);
        return ResponseEntity.ok(result);
    }

}
