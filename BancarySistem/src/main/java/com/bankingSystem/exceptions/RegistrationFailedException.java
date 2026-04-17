package com.project.bankingSystem.exceptions;

/**
 * Lançada quando um registro de nova conta falha
 * @author Alexssandro
 * @since release 2
 * @version 1
 */
public class RegistrationFailedException extends RuntimeException {
    public RegistrationFailedException(String message) {
        super(message);
    }
}
