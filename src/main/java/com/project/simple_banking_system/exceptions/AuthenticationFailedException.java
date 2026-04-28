package com.project.simple_banking_system.exceptions;

public class AuthenticationFailedException extends RuntimeException{

    public AuthenticationFailedException(String message) {
        super(message);
    }
    
}
