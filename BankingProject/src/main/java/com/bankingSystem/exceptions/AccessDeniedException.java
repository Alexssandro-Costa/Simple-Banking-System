package com.bankingSystem.exceptions;

/**
 * Lançada quando o acesso a uma conta bancaria é negado
 * @author Alexssandro
 * @since release 2
 * @version 1
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
    public AccessDeniedException(String message, Exception e) { super(message, e); }
}
