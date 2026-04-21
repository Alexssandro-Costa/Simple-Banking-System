package com.project.simple_banking_system.exceptions;

public class DisabledAccountException extends RuntimeException {

    public DisabledAccountException(String message) {
        super(message);
    }
    
}
