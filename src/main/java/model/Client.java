package model;

import java.time.LocalDate;

public class Client extends Person {

    private Account account;

    public Client(String name, String CPF, String phone, LocalDate DTBirth, String password, Account account) {
        super(name, CPF, new Phone(phone), DTBirth, new Password(password));
        this.account = account;
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
