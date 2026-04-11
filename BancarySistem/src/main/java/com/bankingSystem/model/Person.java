package com.project.bankingSystem.model;

import com.project.bankingSystem.exceptions.InputException;
import java.time.LocalDate;
import java.time.Period;


/**
 * Classe abstrata contêndo informações e metodos de uma pessoa.
 * @author Alexssandro
 * @since release 1
 * @version 1.2
 */
public abstract class Person {

    private String name;
    private CPF cpf;
    private Phone phone;
    private LocalDate DTBirth;
    private Password password;

    protected Person(String name, CPF cpf, Phone phone, LocalDate DTBirth, Password password) {

        validateName(name);
        validateDTBirth(DTBirth);
        cpf.validate();
        password.validate();
        password.encrypt();

        this.name = name.toUpperCase();
        this.cpf = cpf;
        this.phone = phone;
        this.DTBirth = DTBirth;
        this.password = password;
    }

    protected Person(String name, Password password) {

        validateName(name);
        this.password = password;
        this.name = name;
    }

    /**
     * Getter para o campo name.
     * @return String contêndo o contéudo do campo.
     */
    public String getName() {
        return name;
    }

    /**
     * getter para o campo cpf.
     * @return CPF contêndo o contéudo do campo.
     */
    public CPF getCpf() {
        return cpf;
    }

    /**
     * getter para o campo phone.
     * @return Phone contêndo o contéudo do campo.
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Setter para o campo phone
     * @param phone Numero de telefone
     */
    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    /**
     * getter para o campo password.
     * @return Password contêndo o contéudo do campo.
     */
    public Password getPassword() {
        return password;
    }

    /**
     * getter para o campo DTBirth.
     * @return LocalDate contêndo o contéudo do campo.
     */
    public LocalDate getDTBirth() {
        return DTBirth;
    }

    private void validateName(String input) {

        if(input == null)
            throw new InputException("Nome informado é invalido;");
    }


    /**
     * Verifica se a data de nascimento inserida é valida.
     * @param date Data.
     * @exception InputException Lançada se o elemento for invalido.
     * @exception IllegalArgumentException Lançada se saldo bancário não for valido.
     */
    private void validateDTBirth(LocalDate date) {

        // verifica se a data de nascimento inserida é valida

        if(date == null)
            throw new InputException("A Data de nascimento passada é invalida");
        if(date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de nascimento não pode estar no futuro");

        int years = Period.between(date, LocalDate.now()).getYears();
        if(years < 18)
            throw new IllegalArgumentException("O usuario deve ter ao menos 18 anos para se cadastrar");
    }
}
