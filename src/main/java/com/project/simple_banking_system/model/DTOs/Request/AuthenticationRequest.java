package com.project.simple_banking_system.model.DTOs.Request;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(@NotEmpty(message =  "CPF é obrigatorio.") String cpf,
                                    @NotEmpty(message = "Senha é obrigatorio") String password) {
    
}
