package com.project.simple_banking_system.utility;


import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

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


/**
 * Verifica a formatação e a validade dos dados inseridos.
 * @author Alexssandro.
 * @since release 3
 * @version 1
 */
public class ValidateData {

    /**
     * Executa a operação de validação.
     * @param registerRequestDTO Requisição de registro.
     */
    public static void execute(RegisterRequest registerRequestDTO) {

        validateName(new Name(registerRequestDTO.name()));
        validateCpf(new Cpf(registerRequestDTO.cpf()));
        validateDateBirth(new DateBirth( LocalDate.parse(registerRequestDTO.dateBirth())));
        validateGender(registerRequestDTO.gender());
        validatePhone(new Phone(registerRequestDTO.phone()));
        validatePassword(new Password(registerRequestDTO.password()));
    }


    /**
     * Verifica se o nome passado é valido.
     * @param name nome
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public static void validateName(Name name) {

        // elementos são nulos
        if(name == null || name.getValue() == null)
            throw new NullElementException("O Nome passado é invalido.");

        // elemento não está em um formato aceito
        if(!name.isStandardized())
            throw new InvalidFormatException("O nome não está em um formato valido.");
            
    }

    
    /**
     * Verifica se o CPF passado é valido.
     * @param cpf cpf
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public static void validateCpf(Cpf cpf) {

        // elementos são nulos
        if(cpf == null || cpf.getValue() == null)
            throw new NullElementException("O CPF passado é invalido.");

        // elemento não está em um formato aceito
        if(!cpf.isStandardized())
            throw new InvalidFormatException("O CPF não está em um formato valido.");

    } 

    /**
     * Verifica se a data de nascimento inserida é valida.
     * @param dateBirth data de nascimento
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidDateException Lançada se o data for invalida.
     */
    public static void validateDateBirth(DateBirth dateBirth) {

        if(dateBirth == null || dateBirth.getValue() == null) 
            throw new NullElementException("A data de nascimento passada é invalida.");
        
        if(dateBirth.getValue().isAfter(LocalDate.now()))
            throw new InvalidDateException("Data de nascimento não pode estar no futuro.");

        int years = Period.between(dateBirth.getValue(), LocalDate.now()).getYears();
        if(years < 18)
            throw new InvalidDateException("Usuario deve ter mais que 18 anos.");

    }

    /**
     * Verifica se o Genêro passado é valido.
     * @param gender Genêro
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidEnumValueException Lançada quando um valor não está presente no enum testado.
     */
    public static void validateGender(String gender) {

        if(gender == null)
            throw new NullElementException("Genêro passado não é valido.");

        // verifica se o valor passado é um genero existente.
        boolean valid = Arrays.stream(Gender.values())
                       .anyMatch(g -> g.name().equals(gender));

        if(!valid)
            throw new InvalidEnumValueException("Genêro não é uma opção valida.");

    }    
    
    /**
     * Verifica se o numero de telefone passado é valido.
     * @param phone Numero de telefone.
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public static void validatePhone(Phone phone) {

        if(phone == null || phone.getValue() == null)
            throw new NullElementException("O numero de telefone passado é invalido.");
        if(!phone.isStandardized())
            throw new InvalidFormatException("Numero de telefone passado está em um formato não valido.");

    }
    
    /**
     * Verifica se a senha passada é Valida.
     * @param password Senha.
     * @exception NullElementException Lançada se o elemento for nulo.
     * @exception InvalidFormatException Lançada se o elemento não estiver no formato padrão.
     */
    public static void validatePassword(Password password) {

        if(password == null || password.getValue() == null) 
            throw new NullElementException("O a senha passada é invalida");
        if(!password.isStandardized())
            throw new InvalidFormatException("A senha passado está em um formato não valido");

    }
    
}
