package com.bankingSystem.model;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Classe Client contêndo as informações do cliente.
 * @author Alexssandro
 * @since release 1
 * @version 1.2
 */
public class Client extends Person {

    private Account account;

    public Client(String name, CPF cpf, String phone, LocalDate DTBirth, Password password, String accNumber, BigDecimal value) {
        super(name, cpf, new Phone(phone), DTBirth, password);
        this.account = new Account(accNumber, value);

    }

    public Client(String name, String accNumber, BigDecimal value, Password password) {
        super(name, password);
        this.account = new Account(accNumber, value);
    }


    /**
     * getter para o campo account.
     * @return Account contêndo as informações da conta bancaria.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Retorna uma String do objeto.
     * @return String formatada com todas as informações bancarias do cliente.
     */
    public String toString() {
        return String.format("Nome: %s; CPF: %s; Telefone: %s; Data de nascimento: %s; Numero de conta: %s; Balanço: R$:%s %n",
                getName(), getCpf(), getPhone().getNumber(), getDTBirth().toString(), getAccount().getAccountNumber(),
                getAccount().getBalance().toString());
    }

}
