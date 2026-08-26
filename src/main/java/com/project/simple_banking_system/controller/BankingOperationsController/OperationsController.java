package com.project.simple_banking_system.controller.BankingOperationsController;


import com.project.simple_banking_system.model.DTOs.Response.AccountDataResponse;
import com.project.simple_banking_system.model.DTOs.Response.CheckStatementResponse;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.service.use_cases.GetAccountData;
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
import com.project.simple_banking_system.service.use_cases.ChangeAccountStatus;
import com.project.simple_banking_system.service.use_cases.CheckStatement;
import com.project.simple_banking_system.service.use_cases.PerformTransaction;

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
public class OperationsController {

    @Autowired
    private GetAccountData getAccountData;

    @Autowired
    private PerformTransaction performTransaction;

    @Autowired
    private ChangeAccountStatus changeAccountStatus;

    @Autowired
    private CheckStatement checkStatement;

    /**
     * Recupera os dados da conta associada ao cliente autenticado.
     *
     * @return {@link AccountDataResponse} contendo os dados da conta vinculada ao token de acesso utilizado na requisição
     */
    @Operation(
            summary = "Consulta os dados da conta",
            description = "Recupera os dados da conta bancária associada ao cliente autenticado por meio do token de acesso."
    )@PostMapping("/account/data")
    public ResponseEntity<?> getAccountData() {
        var result = getAccountData.execute();
        return ResponseEntity.ok(result);

    }

    /**
     * Realiza uma transação bancária na conta do cliente autenticado.
     *
     * @param transactionRequest dados necessários para realizar a transação
     * @return {@link TransactionResponse} contêndo os dados resultantes da transação realizada
     */
    @Operation(
            summary = "Realiza uma transação bancária",
            description = "Executa uma transação bancária utilizando os dados informados na requisição."
    )@PostMapping("/account/transaction")
    public ResponseEntity<?> performTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        var result = performTransaction.execute(transactionRequest);
        return ResponseEntity.ok(result);   
    }

    /**
     * Desabilita a conta bancária associada ao cliente.
     *
     * @param changeStatusRequest dados necessários para identificar a conta que terá seu status alterado
     * @return resposta sem conteúdo indicando que a operação foi realizada com sucesso
     */
    @Operation(
            summary = "Desabilita uma conta",
            description = "Altera o status da conta bancária para DESABILITADA."
    )@PatchMapping("/account/disable-account")
    public ResponseEntity<Void> disableAccount(@Valid @RequestBody ChangeStatusRequest changeStatusRequest) {
        changeAccountStatus.execute(changeStatusRequest);
        return ResponseEntity.noContent().build(); // Retorna 204

    }

    /**
     * Habilita a conta bancária associada ao cliente.
     *
     * @param changeStatusRequest dados necessários para identificar a conta que terá seu status alterado
     * @return resposta sem conteúdo indicando que a operação foi realizada com sucesso
     */
    @Operation(
            summary = "Habilita uma conta",
            description = "Altera o status da conta bancária para HABILITADA."
    )@PatchMapping("/account/enable-account")
    public ResponseEntity<Void> enableAccount(@Valid @RequestBody ChangeStatusRequest changeStatusRequest) {
        changeAccountStatus.execute(changeStatusRequest);
        return ResponseEntity.noContent().build(); // Retorna 204

    }


    /**
     * Consulta o extrato bancário da conta associada ao cliente autenticado.
     *
     * @return {@link CheckStatementResponse } - extrato contendo as transações realizadas pela conta
     */
    @Operation(
            summary = "Consulta o extrato bancário",
            description = "Recupera todas as transações registradas no extrato da conta bancária associada ao cliente autenticado."
    )@PostMapping("/account/statement")
    public ResponseEntity<?> checkStatement() {
        var result = checkStatement.execute();
        return ResponseEntity.ok(result);
    }

}
