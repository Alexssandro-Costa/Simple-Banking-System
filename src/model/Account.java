package model;

import exceptions.InsufficentFundsException;

import java.math.BigDecimal;

public class Account {

    private BigDecimal balance;
    private Person holder;
    private String accountNumber;

    public Account(Person holder, BigDecimal initialDeposit, String accountNumber) {

        if(holder == null)
            throw new NullPointerException("O titular da conta inserido é nulo");
        if(initialDeposit == null)
            throw new NullPointerException("O deposito inserido é nulo");
        if(accountNumber == null)
            throw new NullPointerException("O numero de conta inserido é nulo");

        this.holder = holder;
        balance = initialDeposit;
        this.accountNumber = accountNumber;
    }
    public Account(Person holder, String accountNumber) {

        this(holder, BigDecimal.valueOf(0.0), accountNumber);
    }

    public BigDecimal getBalance() { return balance;}

    public Person getHolder() { return holder;}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(BigDecimal value) {

        /*
        Adiciona o valor de "value" ao balanço da conta

        @param value: valor de deposito
        @throws IllegalArgumentException: É lançada se o valor de "value" for menor que 0.0
         */

        if(value.doubleValue() < 0.0)
            throw new IllegalArgumentException("O valor do deposito é menor que 0");

        balance = balance.add(value);

    }

    public void withdraw(BigDecimal value) throws InsufficentFundsException{

         /*
        subtrai o valor de "value" do balanço da conta

        @param value: valor de retirada
        @throws InsufficentFundsException: É lançada se o valor de saque for maior que o saldo da conta, ou se o saldo for menor que 1
         */

        if(balance.doubleValue() <= 0.0)
            throw new InsufficentFundsException("Seu balanço é zero. Sem fundos disponiveis para saque");
        if(balance.doubleValue() < value.doubleValue())
            throw new InsufficentFundsException("Fundos insuficientes! O valor de R$:"  + value.doubleValue() + "ultrapassa o valor disponivel para saque.");

        balance = balance.subtract(value);
    }

    public String toString() {

        return String.format("%s Balance: %s %nNumero da conta: %s", holder, balance, accountNumber);
    }

}
