package com.project.simple_banking_system.model.DTOs.Request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChangeStatusRequest(
        @Schema(description = "Novo status da conta", examples = {"HABILITADA", "DESABILITADA"}) String newStatus) {
}
