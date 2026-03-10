package model;

import exceptions.DepositBelowMinimumException;
import exceptions.InsufficentFundsException;
import exceptions.InvalidFormatException;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {

    private BigDecimal balance;
    private final String accountNumber;
    private Person holder;

    public Account(String accountNumber, String name, String cpf, BigDecimal initialDeposit) throws Exception {
        if(initialDeposit == null)
            throw new NullPointerException("O deposito inserido é nulo");
        if(initialDeposit.compareTo(BigDecimal.ZERO) < 0)
            throw new DepositBelowMinimumException();
        if(accountNumber == null)
            throw new NullPointerException("O numero de conta inserido é nulo");
        if(!isValid(accountNumber))
            throw new InvalidFormatException("O numerico de conta inserido está em um formato não valido");

        holder = new Person(name, cpf);
        balance = initialDeposit;
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() { return balance;}

    public String getAccountNumber() {
        return accountNumber;
    }

    public Person getHolder() { return holder; }

    public void deposit(BigDecimal value) {

        /*
        Adiciona o valor de "value" ao balanço da conta

        @param value: valor de deposito
        @throws IllegalArgumentException: É lançada se o valor de "value" for menor que 0.0
         */

        if(value.compareTo(BigDecimal.ZERO) < 0)
            throw new DepositBelowMinimumException();

        balance = balance.add(value);

    }

    public void withdraw(BigDecimal value) throws Exception{

         /*
        subtrai o valor de "value" do balanço da conta

        @param value: valor de retirada
        @throws InsufficentFundsException: É lançada se o valor de saque for maior que o saldo da conta, ou se o saldo for menor que 1
         */

        if(value.compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("ERRO! Valor negativo inserido para saque");
        if(balance.compareTo(BigDecimal.ZERO) <= 0)
            throw new InsufficentFundsException("Seu balanço é zero. Sem fundos disponiveis para saque");
        if(balance.compareTo(value) < 0)
            throw new InsufficentFundsException("Fundos insuficientes! O valor de R$: "  +
                    value.doubleValue() + " ultrapassa o valor disponivel para saque.");

        balance = balance.subtract(value);
    }

    private boolean isValid(String accountNumber) {

        /*
        verifica se um numerico de conta está em um formato valido
         */

        Pattern pattern = Pattern.compile("^\\d{4}$");
        Matcher matcher = pattern.matcher(accountNumber);

        return matcher.find();
    }

    @Override
    public String toString() {
        return String.format("Número da conta: %s; Titular: %s; CPF do titular: %s; Balanço: R$:%s%n",
                accountNumber, holder.getName(), holder.getCPF(), balance);
    }

}
