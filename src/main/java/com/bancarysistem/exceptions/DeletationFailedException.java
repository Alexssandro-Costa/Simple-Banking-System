package com.bancarysistem.exceptions;

/**
 * Lançada quando a exclusão de uma conta falha.
 * @author Alexssandro
 * @since release 2
 * @version 1
 */
public class DeletationFailedException extends RuntimeException {
    public DeletationFailedException(String message) {
        super(message);
    }
}
