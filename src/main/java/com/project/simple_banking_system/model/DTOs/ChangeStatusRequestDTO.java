package com.project.simple_banking_system.model.DTOs;

public record ChangeStatusRequestDTO(String cpf, String password, String newStatus) {
}
