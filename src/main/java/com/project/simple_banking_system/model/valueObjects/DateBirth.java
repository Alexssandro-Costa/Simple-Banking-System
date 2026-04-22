package com.project.simple_banking_system.model.valueObjects;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;

/**
 * Classe que armazena a data de nascimento de um individuo.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Embeddable
public class DateBirth {

    private LocalDate value;

    public DateBirth(LocalDate value) {
        this.value = value;
    
    }

    public DateBirth() {
    } 

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }
    
    
}
