package model;

import exceptions.InvalidFormatException;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Person {

    private String name;
    private CPF CPF;
    private Phone phone;
    private LocalDate DTBirth;
    private Password password;

    protected Person(String name, CPF CPF, Phone phone, LocalDate DTBirth, Password password) {

        if(name == null)
            throw new NullPointerException("O nome passado é Invalido");
        validateDTBirth(DTBirth);

        if(!getCPF().isValidy())
            throw new InvalidFormatException("CPF está em um formato não valido");
        if(!password.isValidy())
            throw new InvalidFormatException("A senha está em um formato não valido");

        this.name = name.toUpperCase();
        this.CPF = CPF;
        this.phone = phone;
        this.DTBirth = DTBirth;
        this.password = password;
    }

    protected Person(String name) { this.name = name;}

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

    private void validateDTBirth(LocalDate date) {

        // verifica se a data de nascimento inserida é valida

        if(DTBirth == null)
            throw new NullPointerException("A Data de nascimento passada é invalida");
        if(date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de nascimento não pode estar no futuro");

        int years = Period.between(date, LocalDate.now()).getYears();
        if(years < 18)
            throw new IllegalArgumentException("O usuario deve ter ao menos 18 anos para se cadastrar");
    }
}
