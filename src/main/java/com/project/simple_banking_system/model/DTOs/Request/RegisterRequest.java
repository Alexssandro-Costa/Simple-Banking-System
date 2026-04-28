package com.project.simple_banking_system.model.DTOs.Request;

public record RegisterRequest(String name, String cpf, String gender, String phone, String dateBirth, String password) {
    
}
