package com.project.simple_banking_system.exceptions;


/**
 * Lançada quando um elemento é nulo.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
public class NullElementException extends RuntimeException {

    public NullElementException(String message) {
        super(message);
    }
    
}
