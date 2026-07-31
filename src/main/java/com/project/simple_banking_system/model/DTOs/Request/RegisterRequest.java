package com.project.simple_banking_system.model.DTOs.Request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterRequest(
        @Schema(description = "Nome do usuário", example = "Alex de Souza")String name,
        @Schema(description = "Cpf do usuário", example = "000.000.000-00")String cpf,
        @Schema(description = "Género do usuário", example = "MASCULINO") String gender,
        @Schema(description = "Telefone do usuário", example = "5522993217879") String phone,
        @Schema(description = "Data de nascimento do usuário.", example = "2000-02-22")String dateBirth,
        @Schema(description = "Senha do usuário", example = "Alex1234") String password) { }
