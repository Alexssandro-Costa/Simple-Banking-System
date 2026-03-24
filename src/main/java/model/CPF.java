package model;

import exceptions.InvalidFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPF {

    private String value;

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

    public boolean isValidy() {

        ///  Verifica se o CPF passado está em um formato valido para consulta

        if (value == null)
            return false;

        Pattern pattern = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }

    public String toString() {
        return value;
    }




}
