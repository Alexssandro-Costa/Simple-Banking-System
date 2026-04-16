package com.project.simple_banking_system.model.valueObjects;

import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

/**
 * Classe que armazena o CPF de um individuo e o formato padrão permitido.
 * @author Alexssandro
 * @since release 2
 * @version 2
 */
@Embeddable
public class Cpf {

    private String value;

    /**  Formato padrão de um CPF. */ 
    //private static Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    public Cpf(String value) {
        this.value = value;
    }

    public Cpf() {
        value = null;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    /* 
    public static Pattern getCPF_PATTERN() {
        return CPF_PATTERN;
    }
        */
    
    
}
