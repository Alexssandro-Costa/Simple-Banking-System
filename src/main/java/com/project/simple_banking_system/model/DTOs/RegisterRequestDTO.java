package com.project.simple_banking_system.model.DTOs;

public record RegisterRequestDTO( String name, String cpf, String gender, String phone, String dateBirth, String password) {
    
}
