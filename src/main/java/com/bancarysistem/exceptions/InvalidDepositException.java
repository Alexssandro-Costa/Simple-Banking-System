package com.bancarysistem.exceptions;

/**
 * Lançada quando um depósito é invalidado.
 * @author Alexssandro
 * @since release 1
 * @version 1
 */
public class InvalidDepositException extends RuntimeException {
    public InvalidDepositException(String message) {
        super(message);
    }
}
