package com.project.simple_banking_system.model.caseUses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.model.DTOs.ClientDTO;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.repository.AccountRepository;

@Service
public class RegisterNewClient {


    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;


    public void execute(ClientDTO clientDTO) {
    
        // incializa uma nova entidadet conta 
        Account account = new Account( new Password(clientDTO.password()));

        // inicializa uma nova entidade cliente
        Client client = new Client(clientDTO, account);

        // define as dependencias
        client.setAccount(account);
        account.setClient(client);

        // salva as entidades
        clientRepository.save(client);
    }
    
}
