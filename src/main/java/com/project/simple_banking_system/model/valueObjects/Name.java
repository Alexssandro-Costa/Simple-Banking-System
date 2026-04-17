package com.project.simple_banking_system.model.valueObjects;

import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;


/**
 * Classe que armazena o nome de um individuo e o formato padrão permitido.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Embeddable
public class Name {

    private String value;

    /**  Formato padrão de um nome. */
    //private Pattern NAME_PATTERN = Pattern.compile("^(?!.*[-'\\s]{2})[A-Za-zÀ-ÖØ-öø-ÿ]+(?:[A-Za-zÀ-ÖØ-öø-ÿ\\s'.-]*[A-Za-zÀ-ÖØ-öø-ÿ])?$");

    public Name(String value) {
        this.value = value.toUpperCase();
    }

    public Name() {
        value = null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /* 
    public Pattern getNAME_PATTERN() {
        return NAME_PATTERN;
    }
        */
    

    
}
