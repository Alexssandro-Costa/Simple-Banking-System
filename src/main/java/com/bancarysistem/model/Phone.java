package com.bancarysistem.model;

import com.bancarysistem.exceptions.InputException;
import com.bancarysistem.exceptions.InvalidFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Classe que armazena e valida um número de telefone
 * @author Alexssandro
 * @version 1.0
 * @since release 1
 */
public class Phone {

    private String number;


     ///Formatação padrão do número de telefone
    private static final Pattern NUMBER_FORMAT = Pattern.compile("^\\d{11}$");

    Phone(String value) {
        number = value;
    }

    /**
     * Getter parao campo number.
     * @return String contendo o número de telefone validado.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Setter para o campo number.
     * @param number Numero de telefone.
     */
    public void setNumber(String number) {
        validate();
        this.number = number;
    }

    /**
     * Verifica se o conteúdo do campo number está no formato padrão.
     * @exception InputException Lançada se o elemento for invalido.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public void validate() {

        if(number == null)
            throw new InputException("Elemento invalido informado.");

        Matcher matcher = NUMBER_FORMAT.matcher(number);

        if(!matcher.matches())
            throw new InvalidFormatException("O numero informado está em um formato não valido.");
    }
}
