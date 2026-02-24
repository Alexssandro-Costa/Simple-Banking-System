package model;

import exceptions.DepositBelowMinimumException;
import exceptions.InsufficentFundsException;

import java.math.BigDecimal;

public class Account extends Person{

    private BigDecimal balance;
    private final String accountNumber;

    public Account(String accountNumber, String name, String cpf, BigDecimal initialDeposit) throws Exception {
        super(name, cpf);

        if(initialDeposit == null)
            throw new NullPointerException("O deposito inserido é nulo");
        if(initialDeposit.doubleValue() < 0)
            throw new DepositBelowMinimumException();
        if(accountNumber == null)
            throw new NullPointerException("O numero de conta inserido é nulo");

        balance = initialDeposit;
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() { return balance;}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(BigDecimal value) {

        /*
        Adiciona o valor de "value" ao balanço da conta

        @param value: valor de deposito
        @throws IllegalArgumentException: É lançada se o valor de "value" for menor que 0.0
         */

        if(value.doubleValue() < 0)
            throw new DepositBelowMinimumException();

        balance = balance.add(value);

    }

    public void withdraw(BigDecimal value) throws InsufficentFundsException{

         /*
        subtrai o valor de "value" do balanço da conta

        @param value: valor de retirada
        @throws InsufficentFundsException: É lançada se o valor de saque for maior que o saldo da conta, ou se o saldo for menor que 1
         */

        if(balance.doubleValue() <= 0)
            throw new InsufficentFundsException("Seu balanço é zero. Sem fundos disponiveis para saque");
        if(balance.doubleValue() < value.doubleValue())
            throw new InsufficentFundsException("Fundos insuficientes! O valor de R$: "  + value.doubleValue() + " ultrapassa o valor disponivel para saque.");

        balance = balance.subtract(value);
    }

    public String toString() {
        return String.format("%s;%s;%s;%s%n", accountNumber, getName(), getCPF(), balance);
    }

}
