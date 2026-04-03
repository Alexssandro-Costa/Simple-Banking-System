package com.bancarysistem.service;

import com.bancarysistem.model.Password;

import java.math.BigDecimal;

/**
 * Interface que define metodos de operações em bancarias.
 * @param <E> Dados de retorno.
 * @author Alexssandro
 * @since release 2
 * @version 1.0
 */
public interface IOperations <E> {

    public E withdraw(String accNumber, Password password, BigDecimal value);

    public E deposit(String accNumber, Password password, BigDecimal value);

    public E transfer(String accNumber, String accTarget, Password password, BigDecimal value);
}
