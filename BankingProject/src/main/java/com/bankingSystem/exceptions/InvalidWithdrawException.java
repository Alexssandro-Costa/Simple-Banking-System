package com.bankingSystem.exceptions;

/**
 * Lançada quando um saque falha.
 * @author Alexssandro
 * @since release 1
 * @version 1
 */
public class InvalidWithdrawException extends RuntimeException {
    public InvalidWithdrawException(String message) {
        super(message);
    }
}
