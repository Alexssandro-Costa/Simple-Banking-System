package com.project.simple_banking_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simple_banking_system.model.DTOs.Request.LoginRequest;
import com.project.simple_banking_system.model.DTOs.Request.RegisterRequest;
import com.project.simple_banking_system.service.caseUses.AuthenticateClient;
import com.project.simple_banking_system.service.caseUses.RegisterNewClient;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
 

    @Autowired 
    RegisterNewClient registerNewClient;

    @Autowired
    AuthenticateClient authenticateClient;


    @GetMapping("/test")
    public String test() {
        return "Olá Mundo.";
    }

    @Operation(summary = "autentica uma tentativa de login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        var result = authenticateClient.execute(loginRequest); 
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Registra um novo cliente", description = "Cria uma conta e um cliente no banco")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        var result = registerNewClient.execute(registerRequest);
        return ResponseEntity.ok(result);
    }

}
