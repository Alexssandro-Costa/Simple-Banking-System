package com.bankingSystem.dto;

/**
 * Classe DTO que armazena dados essênciais de uma conta bancaria.
 * @author Alexssandro
 * @since release 2
 * @version 1.0
 * @param holder Nome do titular da conta.
 * @param accountNumber Numero da conta.
 * @param balance Saldo bancário
 */
public record AccountDTO(String holder, String accountNumber, String balance) {

    @Override
    public String toString() {
        return String.format("Titular: %s %nNumero da conta: %s %nSaldo: R$:%s%n", holder, accountNumber, balance);
    }
}
