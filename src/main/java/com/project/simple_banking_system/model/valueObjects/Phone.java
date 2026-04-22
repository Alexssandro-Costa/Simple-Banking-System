package com.project.simple_banking_system.model.valueObjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

/**
 * Classe que armazena o telefone de um individuo e o formato padrão permitido.
 * @author Alexssandro
 * @since release 1
 * @version 2
 */
@Embeddable
public class Phone {

    private String value;
    
    /**  Formato padrão de um numero de telefone. */
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\(?\\d{2}\\)?\\s?\\d{8,9}$");

    public String getValue() {
        return value;
    }

    public Phone(String value) {
        this.value = value;
    }

    public Phone() {
        value = null;
    }


    public void setValue(String value) {
        this.value = value;
    }

    
    /**
     * Verifica se o valor do objeto está no formato padrão.
     * @return verdadeiro se estiver, falso caso contrario.
     */
    public boolean isStandardized() {

        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(value);
        return matcher.matches();

    }

    
}
