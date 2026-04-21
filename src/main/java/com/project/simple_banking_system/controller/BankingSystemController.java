package com.project.simple_banking_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simple_banking_system.model.DTOs.AccountDTO;
import com.project.simple_banking_system.model.DTOs.ChangeStatusRequestDTO;
import com.project.simple_banking_system.model.DTOs.ErrorMessageDTO;
import com.project.simple_banking_system.model.DTOs.LoginRequestDTO;
import com.project.simple_banking_system.model.DTOs.RegisterRequestDTO;
import com.project.simple_banking_system.model.DTOs.TransactionDTO;
import com.project.simple_banking_system.model.DTOs.TransactionRequestDTO;
import com.project.simple_banking_system.service.caseUses.AccessAccount;
import com.project.simple_banking_system.service.caseUses.ChangeAccountStatus;
import com.project.simple_banking_system.service.caseUses.CheckStatement;
import com.project.simple_banking_system.service.caseUses.PerformTransaction;
import com.project.simple_banking_system.service.caseUses.RegisterNewClient;


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
    public AccountDTO registerNewClient(@RequestBody RegisterRequestDTO registerRequest) {
        return registerNewClient.execute(registerRequest);
    }

    @GetMapping("/login")
    public ResponseEntity<?> AccessAccount(@RequestBody LoginRequestDTO loginRequest) {

        try {
            var result = accessAccount.execute(loginRequest);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            var errorMessage =  new ErrorMessageDTO(e.getMessage());
            return ResponseEntity.status(400).body(errorMessage);
        }
    }

    @PostMapping("/account/{accountNumber}/transaction")
    public void performTransaction(@PathVariable String accountNumber, @RequestBody TransactionRequestDTO transactionRequest) {

        performTransaction.execute(accountNumber, transactionRequest);        
    }

    @PatchMapping("/account/{accountNumber}/disable-account")
    public void disableAccount(@PathVariable String accountNumber, @RequestBody ChangeStatusRequestDTO changeStatusRequestDTO) {
        changeAccountStatus.execute(accountNumber, changeStatusRequestDTO);

    }

    @PatchMapping("/account/{accountNumber}/enable-account")
    public void enableAccount(@PathVariable String accountNumber, @RequestBody ChangeStatusRequestDTO changeStatusRequestDTO) {

        changeAccountStatus.execute(accountNumber, changeStatusRequestDTO);

    }

    @GetMapping("/account/{accountNumber}/statement")
    public List<TransactionDTO> checkStatement(@PathVariable String accountNumber) {

        return checkStatement.execute(accountNumber);
    }

}
