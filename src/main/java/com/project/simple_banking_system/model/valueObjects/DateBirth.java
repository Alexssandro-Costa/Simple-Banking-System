package com.project.simple_banking_system.model.valueObjects;

import java.time.LocalDate;
import java.time.Period;

import com.project.simple_banking_system.exceptions.InvalidDateException;
import com.project.simple_banking_system.exceptions.NullElementException;

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
        this.value = null;
    } 

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }

    /**
     * Verifica se a data de nascimento inserida é valida.
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidDateException Lançada se o data for invalida.
     */
    public void validate() {

        if(value == null)
            throw new NullElementException("A Data de nascimento passada é invalida");

        if(value.isAfter(LocalDate.now()))
            throw new InvalidDateException("Data de nascimento não pode estar no futuro.");

        int years = Period.between(value, LocalDate.now()).getYears();
        if(years < 18)
            throw new InvalidDateException("Usuario deve ter mais que 18 anos.");

    }

    
    
}
