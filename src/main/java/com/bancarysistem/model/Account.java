package com.bancarysistem.model;

import com.bancarysistem.exceptions.InputException;
import com.bancarysistem.exceptions.InvalidDepositException;
import com.bancarysistem.exceptions.InvalidWithdrawException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Classe que armazena e valida a conta bancaria de um cliente.
 * @author Alexssandro
 * @version 1.0
 * @since release 1
 */
public class Account {

    private String accountNumber;
    private BigDecimal balance;
    private Pattern ACCOUNT_NUMBER_FORMAT = Pattern.compile("^\\d{9}$");

    public Account(String accountNumber, BigDecimal balance) {

        verifyAccountNumber(accountNumber);
        verifyBalance(balance);
        this.accountNumber = accountNumber;
        this.balance = balance;

    }

    /**
     * Getter do campo accountNumber.
     * @return O numero da conta bancaria armazenada.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Getter do campo balance
     * @return O saldo bancário armazenado.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Realiza um depósito bancário.
     * @param value Saldo após o depósito.
     * @exception InputException Lançada se o elemento for invalido.
     * @exception InvalidDepositException Lançado se o depósito não for valido.
     */
    public void deposit(BigDecimal value) {

        if(value == null)
            throw new InputException("O valor de deposito inserido é invalido ");

        if(value.compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidDepositException("O valor de deposito inserido não pode ser menor que zero");

        balance = balance.add(value);
    }


    /**
     * Realiza um saque bancário.
     * @param value Saldo após o saque.
     * @exception InputException Lançada se o elemento for invalido.
     * @exception InvalidWithdrawException Lançado se o saque não for valido.
     */
    public void withdraw(BigDecimal value) {

        if(value == null)
            throw new InputException("O valor de saque inserido é invalido");

        if(value.compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidWithdrawException("O valor de saque inserido não pode ser menor que zero");
        if(value.compareTo(balance) > 0)
            throw new InvalidWithdrawException("O valor de saque ultrapassa o limite permitido");

        balance = balance.subtract(value);
    }


    /**
     * Verifica se o elemento passado é um número de conta valido.
     * @param input Numero da conta.
     * @exception InputException Lançada se o elemento for invalido.
     * @exception IllegalArgumentException Lançada se a conta não for valida.
     */
    private void verifyAccountNumber(String input) {

        if(input == null)
            throw new InputException("O numero de conta passado é invalido");

        Matcher matcher = ACCOUNT_NUMBER_FORMAT.matcher(input);
        if(!matcher.matches())
            throw new IllegalArgumentException("ERRO! O numero da conta deve conter 9 caracteres");

    }

    /**
     * Verifica se o saldo bancário é valido.
     * @param input Valor do saldo
     * @exception InputException Lançada se o elemento for invalido.
     * @exception IllegalArgumentException Lançada se saldo bancário não for valido.
     */
    private void verifyBalance(BigDecimal input) {

        if(input == null)
            throw new InputException("O balanço bancario passado é invalido");

        if(input.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("O balanço bancario deve ser maior ou igual a zero");

    }
}
