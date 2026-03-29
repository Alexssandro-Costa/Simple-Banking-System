package com.bancarysistem.model;

import com.bancarysistem.exceptions.InputException;
import com.bancarysistem.exceptions.InvalidFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPF {

    private String value;
    private static final Pattern CPF_FORMAT = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    public CPF(String input) {
        value = input;
    }
    public CPF() { value = null;}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void validate() {

        ///  Verifica se o CPF passado está em um formato valido.

        if (value == null)
            throw new InputException("Elemento não valido informado.");

        Matcher matcher = CPF_FORMAT.matcher(value);

        if(!matcher.matches())
            throw new InvalidFormatException("O cpf passado está em um formato não valido.");
    }

    public String toString() {
        return value;
    }




}
