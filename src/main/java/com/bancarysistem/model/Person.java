package com.bancarysistem.model;

import com.bancarysistem.exceptions.InputException;
import com.bancarysistem.exceptions.InvalidFormatException;

import java.time.LocalDate;
import java.time.Period;

public abstract class Person {

    private String name;
    private CPF CPF;
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
        this.CPF = cpf;
        this.phone = phone;
        this.DTBirth = DTBirth;
        this.password = password;
    }

    protected Person(String name, Password password) {

        validateName(name);
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CPF getCPF() {
        return CPF;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Password getPassword() {
        return password;
    }

    public LocalDate getDTBirth() {
        return DTBirth;
    }

    private void validateName(String input) {

        if(input == null)
            throw new InputException("Nome informado é invalido;");
    }

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
