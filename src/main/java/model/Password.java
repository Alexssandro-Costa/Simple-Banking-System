package model;

import exceptions.InvalidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private String value;

    public Password(String input) {
        validate(input);
    }

    private void validate(String input) {

        // Verifica se o elemento passado está em um formato valido

        if(input == null)
            throw new NullPointerException("A senha passada é invalida");

        if(input.length() < 4)
            throw new InvalidPasswordException("A senha deve conter ao menos 4 caracteres");

        /// determina que a senha deve conter ao menos uma letra e um numero e, ter um minimo de 4 caracteres
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{4,}$");
        Matcher matcher = pattern.matcher(input);

        if(!matcher.matches())
            throw new InvalidPasswordException("Invalida! A senha deve conter ao menos uma letra e um numero");

        value = input;
    }

    public boolean compare(String input) {
        // compara o elemento passado com a senha armazenada

        if(input == null)
            throw new NullPointerException("O elemento passado é invalido");

        return value.equals(input);
    }

    public String getValue() {
        return value;
    }

    // encrypt


}
