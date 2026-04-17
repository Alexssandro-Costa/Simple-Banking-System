package com.project.simple_banking_system.model.valueObjects;

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

    public AccountNumber() {
        value = Generator.generateNumericString(9);
    }

    public AccountNumber (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
