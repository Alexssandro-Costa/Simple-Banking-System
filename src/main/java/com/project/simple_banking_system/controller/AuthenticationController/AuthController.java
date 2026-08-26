package com.project.simple_banking_system.controller.AuthenticationController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simple_banking_system.model.DTOs.Request.AuthenticationRequest;
import com.project.simple_banking_system.model.DTOs.Request.RegisterRequest;
import com.project.simple_banking_system.service.auth.AuthenticateClient;
import com.project.simple_banking_system.service.use_cases.RegisterNewClient;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
 

    @Autowired 
    RegisterNewClient registerNewClient;

    @Autowired
    AuthenticateClient authenticateClient;

    /**
     * Endpoint utilizado para verificar se o controlador de autenticação
     * está disponível e respondendo às requisições.
     *
     * @return mensagem de confirmação da disponibilidade do controlador
     */
    @Operation(
            summary = "Verifica a disponibilidade do controlador",
            description = "Endpoint utilizado para verificar se o controlador de autenticação está ativo e respondendo corretamente."
    )
    @GetMapping("/test")
    public String test() {
        return "Olá Mundo.";
    }

    /**
     * Autentica um cliente a partir de suas credenciais.
     *
     * @param authenticationRequest dados necessários para realizar a autenticação
     * @return um token de acesso pra conta do usuário.
     */
    @Operation(
            summary = "Autentica um cliente",
            description = "Valida as credenciais informadas pelo cliente e realiza o processo de autenticação."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticateClient.execute(authenticationRequest);
        return ResponseEntity.ok(result);
    }

    /**
     * Registra um novo cliente no sistema.
     *
     * @param registerRequest dados necessários para criação do cliente e de sua conta
     * @return um token de acesso pra conta do usuário.
     */
    @Operation(
            summary = "Registra um novo cliente",
            description = "Cria um novo cliente e sua respectiva conta no banco de dados."
    )@PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        var result = registerNewClient.register(registerRequest);
        return ResponseEntity.ok(result);
    }

}
