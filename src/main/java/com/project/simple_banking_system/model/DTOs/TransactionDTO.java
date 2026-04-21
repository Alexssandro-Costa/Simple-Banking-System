package com.project.simple_banking_system.model.DTOs;


public record TransactionDTO(String type, String value, String date, String receiver, String sender ) {

    public String toString () {


        return String.format("Tipo: %s; Valor: $s; data emissão: %s; destinatario: %s; remetente: %s", type, value, date, receiver, sender);


    }
    
}
