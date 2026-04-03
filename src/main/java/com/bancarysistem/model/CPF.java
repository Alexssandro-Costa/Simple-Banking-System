package com.bancarysistem.model;

import com.bancarysistem.exceptions.InputException;
import com.bancarysistem.exceptions.InvalidFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Classe que armazena e valida um CPF.
 * @author Alexssandro
 * @version 1.0
 * @since release 1
 */
public class CPF {

    private String value;

    ///  Formato padrão do CPF.
    private static final Pattern CPF_FORMAT = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    public CPF(String input) {
        value = input;
    }

    public CPF() { value = null; }

    /**
     * Getter do campo value.
     * @return retorna o conteúdo do campo value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter para o campo value.
     * @param input Numero do cpf.
     */
    public void setValue(String input) {
        this.value = value;
    }


    /**
     * Verifica se o conteúdo do campo value está no formato padrão.
     * @exception InputException Lançada se o elemento for invalido.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public void validate() {

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
