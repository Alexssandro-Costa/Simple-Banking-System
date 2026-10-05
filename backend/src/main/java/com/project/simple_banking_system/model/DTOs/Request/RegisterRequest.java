package com.project.simple_banking_system.model.DTOs.Request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterRequest(
        @Schema(description = "Nome do usuário", example = "Alex de Souza")String name,
        @Schema(description = "Cpf do usuário", example = "000.000.000-00")String cpf,
        @Schema(description = "Género do usuário", examples = {"MASCULINO", "FEMININO", "OUTRO"}) String gender,
        @Schema(description = "Telefone do usuário", example = "22998765432") String phone,
        @Schema(description = "Data de nascimento do usuário.", example = "2000-01-01")String dateBirth,
        @Schema(description = "Senha do usuário", example = "Alex1234") String password) { }
