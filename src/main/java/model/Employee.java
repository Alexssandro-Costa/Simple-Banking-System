package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {

    /*
    private BigDecimal salary;

    public Employee(String name, String CPF, String phone, LocalDate DTBirth, String password, BigDecimal salary) {
        super(name, CPF, new Phone(phone), DTBirth, new Password(password));

        verifySalary(salary);
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    private void verifySalary(BigDecimal input) {

        // verifica se a entrada é um salario valido

        if(input == null)
            throw new NullPointerException("O salario passado é invalido");
        if(input.compareTo(BigDecimal.ZERO) < 1)
            throw new IllegalArgumentException("O Salario deve ser maior que 0.");
    }

    public String toString() {

        return String.format("Nome: %s; CPF: %s; Telefone: %s; Data de nascimento: %s; Salario R$:%s %n",
                getName(), getCPF(), getPhone().getNumber(), getDTBirth().toString(), getSalary().toString());
    }

     */
}
