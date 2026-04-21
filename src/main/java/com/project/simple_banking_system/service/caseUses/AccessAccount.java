package com.project.simple_banking_system.service.caseUses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.DisabledAccountException;
import com.project.simple_banking_system.model.DTOs.AccountDTO;
import com.project.simple_banking_system.model.DTOs.LoginRequestDTO;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.repository.AccountRepository;
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
    AccountRepository accountRepository;

    public AccountDTO execute(LoginRequestDTO loginRequest) {


        // busca a conta associada.
        Account acc  = accountRepository.findByAccountNumber(new AccountNumber(loginRequest.accountNumber()));

        // verifica se a conta está ativa
        if(acc.getStatus() == Status.DESABILITADA) {
            throw new DisabledAccountException("A conta está DESABILITADA");
        }

        // inicialize um onjeto DTO
        AccountDTO accountDTO = new AccountDTO(acc.getClient().getName().getValue(), acc.getAccountNumber().getValue(), acc.getBalance().getValue().toString());



        return accountDTO;
    }

    
}
