package com.bancarysistem.model;

import java.math.BigDecimal;

public interface IOperations <E> {

    public E withdraw(String accNumber, Password password, BigDecimal value);

    public E deposit(String accNumber, Password password, BigDecimal value);

    public E transfer(String accNumber, String accTarget, Password password, BigDecimal value);
}
