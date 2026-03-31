package com.bancarysistem.model;

import java.time.LocalDate;

public interface IUserAccount<E> {

    public E login(String accNumber , Password password);

    public E register(String name, CPF cpf, String phoneNumber, LocalDate dtBirth, Password password);

    public void deleteAccount(String accNumber, CPF cpf, Password password);
}
