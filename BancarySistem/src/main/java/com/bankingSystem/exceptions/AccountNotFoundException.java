package com.project.bankingSystem.exceptions;

/**
 * Lançada quando uma conta bancaria não é encontrada.
 * @author Alexssandro
 * @since release 1
 * @version 1
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
