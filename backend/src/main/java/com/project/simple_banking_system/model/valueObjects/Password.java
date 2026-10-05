package com.project.simple_banking_system.model.valueObjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import jakarta.persistence.Embeddable;

/**
 * Classe que armazena a senha de uma conta bancaria.
 * @author Alexssandro
 * @since release 1
 * @version 2
 */
@Embeddable
public class Password {

    private String value;

    /** Formato padrão de uma senha */
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

    public Password(String value) {
        this.value = value;
    }

    public Password() {
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Verifica se o valor do objeto está no formato padrão.
     * @return verdadeiro se estiver, falso caso contrario.
     */
    public boolean isStandardized() {

        Matcher matcher = PASSWORD_PATTERN.matcher(value);
        return matcher.matches();
    }

    
    
}
