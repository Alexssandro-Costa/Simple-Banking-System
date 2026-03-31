package com.bancarysistem.model;

import com.bancarysistem.exceptions.InputException;
import com.bancarysistem.exceptions.InvalidFormatException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private String value;

    /// determina que a senha deve conter ao menos uma letra e um numero e, ter um minimo de 4 caracteres
    private static final Pattern PASSWORD_FORMAT = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{4,}$");

    public Password(String input) {
        value = input;
    }
    public Password() {value = null;}

    public void validate() {

        /// Verifica se o elemento passado está em um formato valido

        if (value == null)
         throw new InputException("Elemento invalido informado.");

        Matcher matcher = PASSWORD_FORMAT.matcher(value);

        if(!matcher.matches())
            throw new InvalidFormatException("A senha está em um formato não valido");
    }

    public boolean compare( String input) {

        /// compara o elemento encriptado o elemento passado

        if(value == null || input == null)
            throw new InputException("Elemento invalido informado");

        return BCrypt.checkpw(input, value);
    }

    public String getValue() {
        return value;
    }

    public void encrypt() {

        // Criptografa a senha

        value = BCrypt.hashpw(value, BCrypt.gensalt());
    }


}
