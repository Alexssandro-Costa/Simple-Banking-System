package com.bankingSystem.exceptions;

/**
 * Lançada quando ocorre um erro no banco de dados
 * @author Alexssandro
 * @since release 2
 * @version 1
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(String message, Exception e) {super(message, e);}
}
