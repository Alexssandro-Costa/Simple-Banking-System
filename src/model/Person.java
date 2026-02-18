package model;

import exceptions.InvalidFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {

    private String name;
    private final String CPF;

    public Person (String name, String CPF) throws Exception{

        if(!isValid(CPF))
            throw new InvalidFormatException("O CPF Passado está em um formato não valido");
        if(name == null)
            throw new NullPointerException("O nome inserido é nulo");

        this.name = name;
        this.CPF = CPF;
    }

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCPF() { return CPF;}

    public String toString() { return String.format("Name: %s %nCPF: %s %n", name, CPF); }

    private static boolean isValid(String CPF) {

        /*
        Verifica se o CPF passado é valido;
        @return retorna verdadeiro se o CPF for valido e falso se for invalido

         */

        Pattern pattern = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
        Matcher matcher = pattern.matcher(CPF);

        return matcher.find();
    }

}
