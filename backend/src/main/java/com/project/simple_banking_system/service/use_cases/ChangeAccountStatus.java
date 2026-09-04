package com.project.simple_banking_system.service.use_cases;

import com.project.simple_banking_system.service.auth.GetTokenData;
import com.project.simple_banking_system.service.util.SearchEntityFromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @version 2.1
 */
@Service
@Transactional
public class ChangeAccountStatus {

    // incializa o repositorio automaticamente
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SearchEntityFromRepository searchEntityFromRepository;

    @Autowired
    private GetTokenData getTokenData;

    /**
     * Muda o status atual de uma conta bancaria.
     * @param changeStatusRequest Requisição de mudança de status.
     */
    public void execute(ChangeStatusRequest changeStatusRequest) {

        // valida as entradas passados
        validate(changeStatusRequest);

        // procura a conta do cliente
        Client client = searchEntityFromRepository.getEntityById(getTokenData.getId(), clientRepository);

        // realiza a mudança de status na conta
        client.getAccount().setStatus(Status.valueOf(changeStatusRequest.newStatus().toUpperCase()));

        // salva a mudança
        clientRepository.save(client);

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
