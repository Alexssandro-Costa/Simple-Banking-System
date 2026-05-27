package com.project.simple_banking_system.utility;


import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.ClientNotFoundException;
import com.project.simple_banking_system.exceptions.InvalidFormatException;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.repository.AccountRepository;
import com.project.simple_banking_system.repository.ClientRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

/**
 * Pesquisa uma entidade no seu respectivo repositório, e a retorna.
 * @author Alexssandro
 * @version 1.0
 * @since release 3
 */
@Component
public class GetEntityFromRepository {


    /**
     * Procura uma conta no repositório.
     * @param accountNumberString Número da conta em formato String.
     * @param repository Repositório de contas.
     * @return Account pertencente ao número de conta passado.
     * @exception InvalidFormatException Lançada quando o número de conta inserido está em um formato não existente.
     * @exception AccountNotFoundException Lançada quando não é possível encontrar uma conta correspondente.
     */
    public Account getAccountByAccountNumber(@NonNull String accountNumberString, @NonNull AccountRepository repository) {
        AccountNumber accountNumber = new AccountNumber(accountNumberString);
        return  getAccountByAccountNumber(accountNumber, repository);
    }

    /**
     * Procura uma conta no repositório.
     * @param accountNumber Número da conta.
     * @param repository Repositório de contas.
     * @return Account pertencente ao número de conta passado.
     * @exception InvalidFormatException Lançada quando o número de conta inserido está em um formato não existente.
     * @exception AccountNotFoundException Lançada quando não é possível encontrar uma conta correspondente.
     */
    public Account getAccountByAccountNumber(@NonNull AccountNumber accountNumber, @NonNull AccountRepository repository) {

        try {
            if(!accountNumber.isStandardized())
                throw new InvalidFormatException("Numero de conta passado não está em um formato aceito.");
            return repository.findByAccountNumber(accountNumber).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new AccountNotFoundException("Não foi possível encontrar a conta de número " + accountNumber.getValue());
        }

    }

    /**
     * Procura um cliente no repositório.
     * @param id Id do usuario cliente.
     * @param repository Repositório de clientes.
     * @return retornar o cliente pertencente ao id passado.
     * @exception ClientNotFoundException Lançada quando não é possível encontrar um cliente com Id correspondente.
     */
    public Client getClientById(@NonNull UUID id, @NonNull ClientRepository repository) {


        try {
            return repository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ClientNotFoundException("Não foi possível encontrar a conta buscada.");
        }

    }

}
