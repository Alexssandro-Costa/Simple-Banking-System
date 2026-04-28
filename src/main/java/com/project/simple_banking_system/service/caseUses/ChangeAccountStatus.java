package com.project.simple_banking_system.service.caseUses;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.InvalidEnumValueException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.ChangeStatusRequestDTO;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.model.valueObjects.TransactionType;
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
     * @exception AccountNotFoundException Lançada quando não é possivel encontrar a conta associada.
     */
    public void execute(String accountNumberString, ChangeStatusRequestDTO changeStatusRequestDTO) {

        // valida os inputs passados
        validate(accountNumberString, changeStatusRequestDTO);

        // procura a conta associada
        Account account = accountRepository.findByAccountNumber(new AccountNumber(accountNumberString));

        if(account == null)
            throw new AccountNotFoundException("Não foi possivel validar a conta.");

        // realiza a mudança
        account.setStatus(Status.valueOf(changeStatusRequestDTO.newStatus()));

        accountRepository.save(account);

    }


    /**
     * Valida os inputs passados para mudança de status.
     * @param accountNumberString Numero da conta.
     * @param changeStatusRequestDTO Requisição de mudança de status.
     * @exception NullElementException Lançada quando um elemento é nulo.
     * @InvalidEnumValueException Lançada quando um valor passado para um enum não é valido.
     */
    private void validate(String accountNumberString, ChangeStatusRequestDTO changeStatusRequestDTO) {


        if(accountNumberString == null) 
            throw new NullElementException("Numero de conta é invalido.");
        if(changeStatusRequestDTO == null || changeStatusRequestDTO.cpf() == null || changeStatusRequestDTO.password() == null || changeStatusRequestDTO.newStatus() == null)
            throw new NullElementException("Requisição de mudança de status invalida.");

        // verifica se o status passado é valido.
        boolean valid = Arrays.stream(TransactionType.values())
                       .anyMatch(g -> g.name().equals(changeStatusRequestDTO.newStatus()));

        if(!valid) {
            throw new InvalidEnumValueException("Status passado é invalido");
        }
    }
    
}
