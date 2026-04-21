package com.project.simple_banking_system.service.caseUses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.model.DTOs.ChangeStatusRequestDTO;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.repository.AccountRepository;

/**
 * Classe de serviço que troca o status atual de uma conta bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Service
@Transactional
public class ChangeAccountStatus {

    // incializa o repositorio automaticamente
    @Autowired
    AccountRepository accountRepository;


    /**
     * Execute a mudança de status.
     * @param accountNumberString Numero da conta relacionada.
     * @param changeStatusRequestDTO Requisição de mudança de status.
     */
    public void execute(String accountNumberString, ChangeStatusRequestDTO changeStatusRequestDTO) {

        // procura a conta associada
        Account account = accountRepository.findByAccountNumber(new AccountNumber(accountNumberString));

        // realiza a mudança
        account.setStatus(Status.valueOf(changeStatusRequestDTO.newStatus()));

        accountRepository.save(account);

    }
    
}
