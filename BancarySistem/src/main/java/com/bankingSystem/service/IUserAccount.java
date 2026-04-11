package com.project.bankingSystem.service;

import com.project.bankingSystem.exceptions.AccessDeniedException;
import com.project.bankingSystem.model.CPF;
import com.project.bankingSystem.model.Password;

import java.time.LocalDate;

/**
 * Interface que define métodos de acesso, criação, exclusão e autenticação.
 * @param <E> - Dados de retorno.
 * @author Alexssandro
 * @since release 2
 * @version 1.0
 */
public interface IUserAccount<E> {

    public E login(String accNumber , Password password);

    public E register(String name, CPF cpf, String phoneNumber, LocalDate dtBirth, Password password);

    public void deleteAccount(String accNumber, CPF cpf, Password password);


    /**
     * Realiza a autenticação de entre duas senhas.
     * @param accountPassword Senha da conta bancaria .
     * @param inputPassword Senha inserida.
     * @exception AccessDeniedException Ocorre quando a senha passada não coincide com a senha da conta.
     */
    public default void authentication(Password accountPassword, Password inputPassword) {

        if(!accountPassword.compare(inputPassword.getValue()))
            throw new AccessDeniedException("Não foi permitido o acesso a conta.");
    }

}
