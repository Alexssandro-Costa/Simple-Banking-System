package com.project.simple_banking_system.exceptions;

/**
 * Exceção que dever ser lançada quando a busca por uma entidade em um repositório existente falhar.
 * @author Alexssandro
 * @since 03/08/2026
 * @version 1.0
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
