package model;

import exceptions.InvalidFormatException;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Person {

    private String name;
    private String CPF;
    private Phone phone;
    private LocalDate DTBirth;
    private Password password;

    protected Person(String name, String CPF, Phone phone, LocalDate DTBirth, Password password) {

        if(name == null)
            throw new NullPointerException("O nome passado é Invalido");
        if(CPF == null)
            throw new NullPointerException("O CPF passado é invalido");
        if(phone == null)
            throw new NullPointerException("O telefone passado é invalido");
        if(DTBirth == null)
            throw new NullPointerException("A Data de nascimento passada é invalida");
        if(password == null)
            throw new NullPointerException("A senha passada é invalida");

        verifyCPF(CPF);
        verifyDTBirth(DTBirth);

        this.name = name.toUpperCase();
        this.CPF = CPF;
        this.phone = phone;
        this.DTBirth = DTBirth;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getCPF() {
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

    private void verifyCPF(String input) {

        /*
        Verifica se o elemento passado está em um formato valido;
        @return retorna verdadeiro se valido e falso se for invalido

         */

        Pattern pattern = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
        Matcher matcher = pattern.matcher(input);

        if(!matcher.matches())
            throw new InvalidFormatException("O CPF passado está em um formato não valido.");
    }

    private void verifyDTBirth(LocalDate date) {

        // verifica se a data de nascimento inserida é valida


        if(date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de nascimento não pode estar no futuro");

        int years = Period.between(date, LocalDate.now()).getYears();
        if(years < 18)
            throw new IllegalArgumentException("O usuario deve ter ao menos 18 anos para se cadastrar");
    }
}
