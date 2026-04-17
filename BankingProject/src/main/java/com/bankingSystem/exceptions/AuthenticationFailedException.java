package com.bankingSystem.exceptions;

/**
 * Lançada quando uma autenticação falha.
 * @author Alexssandro
 * @since release 2
 * @version 1
 */
public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
