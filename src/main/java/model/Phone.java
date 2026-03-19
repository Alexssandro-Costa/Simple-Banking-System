package model;

import exceptions.InvalidFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phone {

    private String number;

    Phone(String value) {

        isValid(value);
        number = value;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private void isValid(String value) {

        // Verifica se a String passada está em um formato de um numero de telefone valido

        if(value == null)
            throw new NullPointerException("O numero de telefone passado é Invalido.");

        Pattern pattern = Pattern.compile("^\\d{11}$\n");
        Matcher matcher = pattern.matcher(value);

        if(matcher.matches())
            throw new InvalidFormatException("O numero está em um formato não valido");
    }
}
