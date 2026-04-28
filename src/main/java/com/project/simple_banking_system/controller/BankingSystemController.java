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
import com.project.simple_banking_system.model.DTOs.TransactionRequestDTO;

// classes de serviço
import com.project.simple_banking_system.service.caseUses.AccessAccount;
import com.project.simple_banking_system.service.caseUses.ChangeAccountStatus;
import com.project.simple_banking_system.service.caseUses.CheckStatement;
import com.project.simple_banking_system.service.caseUses.PerformTransaction;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;


/**
 * Controller do sistema que centraliza as requisições.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Tag(name = "Banking System", description = "Operações bancárias principais")
@RequestMapping("/api/banco")
@RestController
public class BankingSystemController {

    @Autowired
    private AccessAccount accessAccount;

    @Autowired
    private PerformTransaction performTransaction;

    @Autowired
    private ChangeAccountStatus changeAccountStatus;

    @Autowired
    private CheckStatement checkStatement;

    @PostMapping("/account/{accountNumber}/transaction")
    public ResponseEntity<?> performTransaction(@Valid @PathVariable String accountNumber, @Nonnull @RequestBody TransactionRequestDTO transactionRequest) {

        var result = performTransaction.execute(accountNumber, transactionRequest);
        return ResponseEntity.ok(result);   
    }

    @PatchMapping("/account/{accountNumber}/disable-account")
    public ResponseEntity<Void> disableAccount(@Valid @PathVariable String accountNumber, @Nonnull @RequestBody ChangeStatusRequestDTO changeStatusRequestDTO) {
        changeAccountStatus.execute(accountNumber, changeStatusRequestDTO);
        return ResponseEntity.noContent().build(); // Retorna 204

    }

    @PatchMapping("/account/{accountNumber}/enable-account")
    public ResponseEntity<Void> enableAccount(@Valid @PathVariable String accountNumber, @Nonnull @RequestBody ChangeStatusRequestDTO changeStatusRequestDTO) {
        changeAccountStatus.execute(accountNumber, changeStatusRequestDTO);
        return ResponseEntity.noContent().build(); // Retorna 204

    }

    @GetMapping("/account/{accountNumber}/statement")
    public ResponseEntity<?> checkStatement(@Valid @PathVariable String accountNumber) {

        var result = checkStatement.execute(accountNumber);
        return ResponseEntity.ok(result);
    }

}
