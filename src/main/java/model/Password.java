package model;

import exceptions.InputException;
import exceptions.InvalidPasswordException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private String value;
    private static final String PASSWORD_FORMAT = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{4,}$";

    public Password(String input) {
        value = input;
    }
    public Password() {value = null;}

    public boolean isValidy() {

        /// Verifica se o elemento passado está em um formato valido
        /// @return Retorna true se for valido e false se for invalido

        if (value == null)
            return false;

        if (value.length() < 4)
            return false;

        /// determina que a senha deve conter ao menos uma letra e um numero e, ter um minimo de 4 caracteres
        Pattern pattern = Pattern.compile(PASSWORD_FORMAT);
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }

    public static boolean compare(String encryptedPassword, String input) {

        /// compara o elemento encriptado o elemento passado

        if(encryptedPassword == null || input == null)
            throw new InputException("Elemento não valido informado");

        return BCrypt.checkpw(input, encryptedPassword);
    }

    public String getValue() {
        return value;
    }

    public static String encrypt(String input) {

        return BCrypt.hashpw(input, BCrypt.gensalt());
    }


}
