package com.project.simple_banking_system.utility;


import com.project.simple_banking_system.exceptions.EntityNotFoundException;
import com.project.simple_banking_system.exceptions.InvalidFormatException;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.repository.AccountRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * Pesquisa uma entidade no seu respectivo repositório, e a retorna.
 * @author Alexssandro
 * @version 2.0
 * @since release 3
 */
@Component
public class SearchEntityFromRepository {


    /**
     * Procura uma conta em um repositório existente.
     * @param accountNumberString Número da conta em formato de String.
     * @param repository Repositório de contas.
     * @return conta pertencente ao número passado.
     * @exception InvalidFormatException Lançada quando o número de conta inserido está em um formato não existente.
     * @exception EntityNotFoundException Lançada quando não é possível encontrar uma conta correspondente.
     */
    public Account getAccountByAccountNumber(@NonNull String accountNumberString, @NonNull AccountRepository repository) {
        AccountNumber accountNumber = new AccountNumber(accountNumberString);
        return  getAccountByAccountNumber(accountNumber, repository);
    }

    /**
     * Procura uma conta em um repositório existente.
     * @param accountNumber Número da conta.
     * @param repository Repositório de contas.
     * @return Account pertencente ao número passado.
     * @exception InvalidFormatException Lançada quando o número de conta inserido está em um formato não existente.
     * @exception EntityNotFoundException Lançada quando não é possível encontrar uma conta correspondente.
     */
    public Account getAccountByAccountNumber(AccountNumber accountNumber, AccountRepository repository) {

        try {
            if(!accountNumber.isStandardized())
                throw new InvalidFormatException("Numero de conta passado não está em um formato aceito.");
            return repository.findByAccountNumber(accountNumber).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Não foi possível encontrar a conta de número " + accountNumber.getValue());
        }

    }

    /**
     * Procura uma Entidade em um repositório JPA.
     * @param id id do usuario cliente.
     * @param repository Repositório de entidades.
     * @return retorna uma entidade existente.
     * @exception EntityNotFoundException Lançada quando não é possível encontrar um cliente com Id correspondente.
     */
    public <T, ID> T getEntityById(ID id, JpaRepository<T, ID> repository) {

        try {
            return repository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Não foi possível encontrar a conta buscada.");
        }

    }

}
