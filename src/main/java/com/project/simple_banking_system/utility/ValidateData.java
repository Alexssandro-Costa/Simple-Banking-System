package com.project.simple_banking_system.utility;


import java.time.LocalDate;
import java.time.Period;

import com.project.simple_banking_system.exceptions.InvalidDateException;
import com.project.simple_banking_system.exceptions.InvalidEnumValueException;
import com.project.simple_banking_system.exceptions.InvalidFormatException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Request.RegisterRequest;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.valueObjects.DateBirth;
import com.project.simple_banking_system.model.valueObjects.Gender;
import com.project.simple_banking_system.model.valueObjects.Name;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.model.valueObjects.Phone;
import org.springframework.stereotype.Component;


/**
 * Verifica a formatação e a validade dos dados inseridos.
 * @author Alexssandro.
 * @since release 3
 * @version 1
 */
@Component
public class ValidateData {


    /**
     * Verifica se o nome passado é valido.
     * @param value Valor do nome
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public void validateName(String value) {

        // elementos são nulos
        if(value == null)
            throw new NullElementException("O Nome informado não pode ser nulo.");
        Name name = new Name(value);

        // elemento não está num formato aceito
        if(!name.isStandardized())
            throw new InvalidFormatException("O nome informado está em um formato não valido.");
            
    }

    
    /**
     * Verifica se o CPF passado é valido.
     * @param value valor do cpf
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public void validateCpf(String value) {

        // elementos são nulos
        if(value == null)
            throw new NullElementException("O CPF informado não pode ser nulo.");
        Cpf cpf = new Cpf(value);

        // elemento não está em um formato aceito
        if(!cpf.isStandardized())
            throw new InvalidFormatException("O CPF informado está em um formato não valido.");

    } 

    /**
     * Verifica se a data de nascimento inserida é valida.
     * @param value data de nascimento
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidDateException Lançada se o data for invalida.
     */
    public void validateDateBirth(String value) {

        if(value == null)
            throw new NullElementException("A data de nascimento não pode ser nula.");
        DateBirth dtBirth = new DateBirth(LocalDate.parse(value));

        if(dtBirth.getValue().isAfter(LocalDate.now()))
            throw new InvalidDateException("Data de nascimento não pode estar no futuro.");

        int years = Period.between(dtBirth.getValue(), LocalDate.now()).getYears();
        if(years < 18)
            throw new InvalidDateException("Usuario deve ter mais que 18 anos.");

    }

    /**
     * Verifica se o Genêro passado é valido.
     * @param gender Genêro
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidEnumValueException Lançada quando um valor não está presente no enum testado.
     */
    public void validateGender(String gender) {

        if(gender == null)
            throw new NullElementException("Genêro não pode ser nulo.");

        // verifica se o valor passado é um genero existente.
        try {
            // tenta converter a string para um enum
            Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("Genêro informado não é uma opção valida.");
        }

    }    
    
    /**
     * Verifica se o numero de telefone passado é valido.
     * @param value Numero de telefone.
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public void validatePhone(String value) {

        if(value == null)
            throw new NullElementException("O numero de telefone passado é invalido.");
        Phone phone = new Phone(value);

        if(!phone.isStandardized())
            throw new InvalidFormatException("Numero de telefone passado está em um formato não valido.");

    }
    
    /**
     * Verifica se a senha passada é Valida.
     * @param value Senha.
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public void validatePassword(String value) {

        if(value == null)
            throw new NullElementException("O a senha passada é invalida");
        Password password = new Password(value);

        if(!password.isStandardized())
            throw new InvalidFormatException("A senha passado está em um formato não valido");

    }
    
}
