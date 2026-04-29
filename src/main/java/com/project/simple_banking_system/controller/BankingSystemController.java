package com.project.simple_banking_system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simple_banking_system.model.DTOs.Request.ChangeStatusRequest;
import com.project.simple_banking_system.model.DTOs.Request.TransactionRequest;
// classes de serviço
import com.project.simple_banking_system.service.caseUses.AccessAccount;
import com.project.simple_banking_system.service.caseUses.ChangeAccountStatus;
import com.project.simple_banking_system.service.caseUses.CheckStatement;
import com.project.simple_banking_system.service.caseUses.PerformTransaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


/**
 * Controller do sistema que centraliza as requisições.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Tag(name = "Banking System", description = "Operações bancárias principais")
@RequestMapping("/api/bank")
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


    @Operation(description = "Recupera os dados de conta associada ao token de acesso.")
    @PostMapping("/account/access")
    public ResponseEntity<?> accessAccount() {
        var result = accessAccount.execute();
        return ResponseEntity.ok(result);

    }

    @Operation(description = "Realiza transações de retirada, deposito e transferências bancarias.")
    @PostMapping("/account/transaction")
    public ResponseEntity<?> performTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        var result = performTransaction.execute(transactionRequest);
        return ResponseEntity.ok(result);   
    }

    @Operation(description = "Muda o Status de uma conta para DESABILITADA.")
    @PatchMapping("/account/disable-account")
    public ResponseEntity<Void> disableAccount(@Valid @RequestBody ChangeStatusRequest changeStatusRequest) {
        changeAccountStatus.execute(changeStatusRequest);
        return ResponseEntity.noContent().build(); // Retorna 204

    }

    @Operation(description = "Muda o Status de uma conta para HABILITADA.")
    @PatchMapping("/account/enable-account")
    public ResponseEntity<Void> enableAccount(@Valid @RequestBody ChangeStatusRequest changeStatusRequest) {
        changeAccountStatus.execute(changeStatusRequest);
        return ResponseEntity.noContent().build(); // Retorna 204

    }


    @Operation(description = "Busca e retorna todo o extrato bancario de uma conta associada.")
    @PostMapping("/account/statement")
    public ResponseEntity<?> checkStatement() {
        var result = checkStatement.execute();
        return ResponseEntity.ok(result);
    }

}
