package com.bancarysistem.exceptions;

/**
 * Lançada quando uma senha é invalida
 * @author Alexssandro
 * @since release 1
 * @version 1
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
