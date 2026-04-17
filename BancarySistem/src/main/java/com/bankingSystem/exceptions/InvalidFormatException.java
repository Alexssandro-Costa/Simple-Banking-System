package com.project.bankingSystem.exceptions;

/**
 * Lançada quando um elemento não está em uma formatação correta
 * @author Alexssandro
 * @since release 1
 * @version 1
 */
public class InvalidFormatException extends IllegalArgumentException {

    public InvalidFormatException(String message) {
        super(message);
    }
    public  InvalidFormatException() {
        super("O argumento passado está em um formato não valido");
    }
}
