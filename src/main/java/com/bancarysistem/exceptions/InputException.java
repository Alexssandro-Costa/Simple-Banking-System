package com.bancarysistem.exceptions;

/**
 * Lançada quando um elemento inserido e nulo ou invalido.
 * @author Alexssandro
 * @since release 2
 * @version 1
 */
public class InputException extends RuntimeException {
    public InputException(String message) {
        super(message);
    }
}
