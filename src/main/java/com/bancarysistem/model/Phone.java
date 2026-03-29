package com.bancarysistem.model;

import com.bancarysistem.exceptions.InputException;
import com.bancarysistem.exceptions.InvalidFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phone {

    private String number;
    private static final Pattern NUMBER_FORMAT = Pattern.compile("^\\d{11}$");

    Phone(String value) {
        number = value;
    }

    public String getNumber() {

        validate();
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void validate() {

        /// Verifica se a String passada está em um formato de numero de telefone valido

        if(number == null)
            throw new InputException("Elemento invalido informado.");

        Matcher matcher = NUMBER_FORMAT.matcher(number);

        if(!matcher.matches())
            throw new InvalidFormatException("O numero informado está em um formato não valido.");
    }
}
