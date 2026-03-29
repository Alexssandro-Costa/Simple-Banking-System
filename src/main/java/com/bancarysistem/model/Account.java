package com.bancarysistem.model;

import com.bancarysistem.exceptions.InputException;
import com.bancarysistem.exceptions.InvalidDepositException;
import com.bancarysistem.exceptions.InvalidWithdrawException;

import java.math.BigDecimal;

public class Account {

    private String accountNumber;
    private BigDecimal balance;

    public Account(String accountNumber, BigDecimal balance) {

        verifyAccountNumber(accountNumber);
        verifyBalance(balance);
        this.accountNumber = accountNumber;
        this.balance = balance;

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal value) {

        // realiza uma inserção de valor no balanço bancario

        if(value == null)
            throw new InputException("O valor de deposito inserido é invalido ");

        if(value.compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidDepositException("O valor de deposito inserido não pode ser menor que zero");

        balance = balance.add(value);
    }

    public void withdraw(BigDecimal value) {

        // realiza uma retirada de valor no balanço bancario

        if(value == null)
            throw new InputException("O valor de saque inserido é invalido");

        if(value.compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidWithdrawException("O valor de saque inserido não pode ser menor que zero");
        if(balance.subtract(value).compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidWithdrawException("O valor de saque ultrapassa o limite permitido");

        balance = balance.subtract(value);
    }


    private void verifyAccountNumber(String input) {

        // verifica se o numero de conta está em um formato valido

        if(input == null)
            throw new InputException("O numero de conta passado é invalido");

        if(input.length() != 9)
            throw new IllegalArgumentException("ERRO! O numero da conta deve conter 9 caracteres");
    }

    private void verifyBalance(BigDecimal input) {

        // verifica se o valor passado pro balanço é valido

        if(input == null)
            throw new InputException("O balanço bancario passado é invalido");

        if(input.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("O balanço bancario deve ser maior ou igual a zero");

    }
}
