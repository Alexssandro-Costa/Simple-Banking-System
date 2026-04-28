package com.project.simple_banking_system.service.caseUses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.DisabledAccountException;
import com.project.simple_banking_system.model.DTOs.AccountDTO;
import com.project.simple_banking_system.model.DTOs.Request.LoginRequest;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.repository.AccountRepository;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.model.entity.Account;


/**
 * Classe de serviço que garante acesso a uma conta bancaria existente.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Service
public class AccessAccount {


    // inicializa automaticamente o repositorio. 
    @Autowired
    ClientRepository clientRepository;

  
    public AccountDTO execute(LoginRequest loginRequest) {


        // busca a conta associada.
        Account acc  = new Account();

        // verifica se a conta está ativa
        if(acc.getStatus() == Status.DESABILITADA) {
            throw new DisabledAccountException("A conta está DESABILITADA");
        }

        // inicialize um objeto DTO
        AccountDTO accountDTO = new AccountDTO(acc.getClient().getName().getValue(), 
        acc.getAccountNumber().getValue(), 
        acc.getBalance().getValue().toString());


        return accountDTO;
    }


    
}
