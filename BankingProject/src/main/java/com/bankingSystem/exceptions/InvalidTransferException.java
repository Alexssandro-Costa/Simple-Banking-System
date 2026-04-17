package com.bankingSystem.exceptions;

/**
 * Lançada quando uma transferência falha.
 * @author Alexssandro
 * @since release 2
 * @version 1
 */
public class InvalidTransferException extends RuntimeException {
    public InvalidTransferException(String message) {
        super(message);
    }
}
