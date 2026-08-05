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


    @GetMapping("/test")
    public String test() {
        return "Olá Mundo.";
    }

    @Operation(summary = "autentica uma tentativa de login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticateClient.execute(authenticationRequest);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Registra um novo cliente", description = "Cria uma conta e um cliente no banco")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        var result = registerNewClient.register(registerRequest);
        return ResponseEntity.ok(result);
    }

}
