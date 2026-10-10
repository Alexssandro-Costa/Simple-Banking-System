package com.project.simple_banking_system.exceptions;

/**
 * AccountDataRequestFailed - Lanaçado quando Uma requisição sobre os dados de uma conta falha
 */
public class AccountDataRequestFailedException extends RuntimeException{
    public AccountDataRequestFailedException(String message) {
        super(message);
    }
}