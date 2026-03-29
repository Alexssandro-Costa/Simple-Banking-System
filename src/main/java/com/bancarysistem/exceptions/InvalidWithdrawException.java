package com.bancarysistem.exceptions;

public class InvalidWithdrawException extends RuntimeException {
    public InvalidWithdrawException(String message) {
        super(message);
    }
}
