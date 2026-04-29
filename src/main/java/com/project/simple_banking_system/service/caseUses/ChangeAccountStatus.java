package com.project.simple_banking_system.service.caseUses;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.InvalidEnumValueException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Request.ChangeStatusRequest;
import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.model.valueObjects.Status;

import com.project.simple_banking_system.repository.ClientRepository;

/**
 * Classe de serviço que troca o status atual de uma conta bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 2.0
 */
@Service
@Transactional
public class ChangeAccountStatus {

    // incializa o repositorio automaticamente
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DecodeToken decodeToken;

    /**
     * Muda o status atual de uma conta bancaria.
     * @param changeStatusRequest Requisição de mudança de status.
     * @exception AccountNotFoundException Lançada quando não é possivel encontrar a conta associada.
     */
    public void execute(ChangeStatusRequest changeStatusRequest) {

        // valida os inputs passados
        validate(changeStatusRequest);

        try{
            // procura a conta associada
            Client client = clientRepository.findById(decodeToken.execute()).orElseThrow();

            // Insere o novo status da conta
            client.getAccount().setStatus(Status.valueOf(changeStatusRequest.newStatus().toUpperCase()));

            // salva o mudança
            clientRepository.save(client);

        }catch(NoSuchElementException e) {
            throw new AccountNotFoundException("Não foi possivel encontrar a conta associada.");
        }


    }


    /**
     * Valida os inputs passados para mudança de status.
     * @param changeStatusRequest Requisição de mudança de status.
     * @exception NullElementException Lançada quando um elemento é nulo.
     * @exception InvalidEnumValueException Lançada quando um valor passado para um enum não é valido.
     */
    private void validate(ChangeStatusRequest changeStatusRequest) {

        if(changeStatusRequest == null || changeStatusRequest.newStatus() == null)
            throw new NullElementException("Requisição de mudança de status invalida.");

        // verifica se o status passado é um enum valido.
        try {
            //tenta converter a string para um enum
            Status.valueOf(changeStatusRequest.newStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("Status passado é invalido");
        }
        
    }
    
}
