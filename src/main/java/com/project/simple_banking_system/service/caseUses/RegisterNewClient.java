package com.project.simple_banking_system.service.caseUses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.model.DTOs.AccountDTO;
import com.project.simple_banking_system.model.DTOs.RegisterRequestDTO;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.repository.ClientRepository;


/**
 * Classe de serviço que cria uma nova entidade Cliente e Conta.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Service
public class RegisterNewClient {


    /// Inicializa automaticamente o repositorio. 
    @Autowired
    ClientRepository clientRepository;


    @Transactional
    public AccountDTO execute(RegisterRequestDTO registerRequest) {

        // inicializa uma nova entidade cliente
        Client client = new Client(registerRequest);

         // incializa uma nova entidade conta 
        Account account = new Account( new Password(registerRequest.password()));

        // define as dependencias
        client.setAccount(account);
        account.setClient(client);

        // salva as entidades no banco de dados
        clientRepository.save(client);

        return new AccountDTO(account.getClient().getName().getValue(), account.getAccountNumber().getValue(), account.getBalance().getValue().toString());
    }
    
}
