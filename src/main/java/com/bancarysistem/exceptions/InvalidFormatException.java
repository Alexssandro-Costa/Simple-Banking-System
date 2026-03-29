package com.bancarysistem.exceptions;

public class InvalidFormatException extends IllegalArgumentException {

    public InvalidFormatException(String message) {
        super(message);
    }
    public  InvalidFormatException() {
        super("O argumento passado está em um formato não valido");
    }
}
