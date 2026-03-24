package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Client extends Person {

    private Account account;

    public Client(String name, CPF CPF, String phone, LocalDate DTBirth, Password password, String accNumber, BigDecimal value) {
        super(name, CPF, new Phone(phone), DTBirth, password);
        this.account = account;
    }

    public Client(String name, String accNumber, BigDecimal value) {
        super(name);
        this.account = new Account(accNumber, value);
    }

    public Account getAccount() {
        return account;
    }

    public String toString() {
        return String.format("Nome: %s; CPF: %s; Telefone: %s; Data de nascimento: %s; Numero de conta: %s; Balanço: R$:%s %n",
                getName(), getCPF(), getPhone().getNumber(), getDTBirth().toString(), getAccount().getAccountNumber(),
                getAccount().getBalance().toString());
    }
}
