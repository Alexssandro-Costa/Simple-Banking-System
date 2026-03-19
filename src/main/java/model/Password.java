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

        if(input.length() != 5)
            throw new InvalidPasswordException("Invalida! A senha deve conter ao menos 5 caracteres.");

        if(input.isBlank()){
            throw new InvalidPasswordException("Invalida! A senha não deve conter espaços em branco");
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{5}$");
        Matcher matcher = pattern.matcher(input);

        if(!matcher.matches())
            throw new InvalidPasswordException("Invalida! A senha deve conter ao menos uma letra e um numero.");

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
