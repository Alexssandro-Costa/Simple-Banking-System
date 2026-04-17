package com.project.bankingSystem.model;

import com.project.bankingSystem.exceptions.InputException;
import com.project.bankingSystem.exceptions.InvalidFormatException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe que armazena e valida uma senha.
 * @author Alexssandro
 * @version 1.0
 * @since release 1
 */
public class Password {

    private String value;


    ///Formatação padrão de uma senha.
    private static final Pattern PASSWORD_FORMAT = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{4,}$");

    public Password(String input) {
        value = input;
    }
    public Password() {value = null;}

    /**
     * Verifica se o conteúdo da senha está na formatação padrão.
     *  @exception InputException Lançada se o elemento for invalido.
     *  @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public void validate() {

        if (value == null)
         throw new InputException("Elemento invalido informado.");

        Matcher matcher = PASSWORD_FORMAT.matcher(value);
        if(!matcher.matches())
            throw new InvalidFormatException("A senha está em um formato não valido");
    }


    /**
     * Compara o elemento passado com a senha criptógrafa armazenada.
     * @param input Elemento a ser comparado.
     * @return true se os elementos forem iguais, false caso contrario.
     * @exception InputException Lançada se o elemento for invalido.
     */
    public boolean compare(String input) {

        if(value == null || input == null)
            throw new InputException("Elemento invalido informado");

        return BCrypt.checkpw(input, value);
    }

    /**
     * Getter do campo value armazenado.
     * @return O conteúdo do campo value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Criptógrafa o contéudo do campo value armazenado.
     */
    public void encrypt() {

        value = BCrypt.hashpw(value, BCrypt.gensalt());
    }


}
