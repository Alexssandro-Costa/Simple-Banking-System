package com.project.simple_banking_system.exceptions;

public class TokenDecodificationFailedException extends RuntimeException{

    public TokenDecodificationFailedException(String message) {
        super(message);
    }
    
}
