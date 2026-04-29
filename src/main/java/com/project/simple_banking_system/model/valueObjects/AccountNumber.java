package com.project.simple_banking_system.model.valueObjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.project.simple_banking_system.utility.Generator;

import jakarta.persistence.Embeddable;


/**
 * Classe que armazena o numero de uma conta bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Embeddable
public class AccountNumber {

    private String value;

    /**  padrão de um numero de conta. */
    public static final Pattern ACCOUNTNUMBER_PATTERN = Pattern.compile("^\\d{9}$");

    public AccountNumber() {
        value = Generator.generateNumericString(9);
    }

    public AccountNumber (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    
    /**
     * Verifica se o valor do objeto está no formato padrão.
     * @return verdadeiro se estiver, falso caso contrario.
     */
    public boolean isStandardized() {

        Matcher matcher = ACCOUNTNUMBER_PATTERN.matcher(value);
        return matcher.matches();
    }



}
