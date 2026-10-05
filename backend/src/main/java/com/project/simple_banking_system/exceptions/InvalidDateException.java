package com.project.simple_banking_system.exceptions;


/**
 * Exceção que pode ser lançada caso uma date não seja valida.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
public class InvalidDateException extends RuntimeException {

    public InvalidDateException(String message) {
        super(message);
    }
    
}
