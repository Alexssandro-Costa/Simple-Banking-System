package com.project.simple_banking_system.exceptions;

/**
 * Lançada quando uma Transação é invalida.
 */
public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(String message) {
        super(message);
    }
    
}
