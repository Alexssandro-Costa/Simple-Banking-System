package com.project.simple_banking_system.service.use_cases;

import com.project.simple_banking_system.service.auth.DecodeToken;
import com.project.simple_banking_system.utility.GetEntityFromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.DisabledAccountException;

import com.project.simple_banking_system.model.DTOs.Response.AccessAccountResponse;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.model.entity.Client;


/**
 * Classe de serviço que recupera os dados de uma conta bancaria existente.
 * @author Alexssandro
 * @since release 3
 * @version 2.0
 */
@Service
public class GetAccountData {


    // inicializa automaticamente o repositorio. 
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DecodeToken decodeToken;
    @Autowired
    GetEntityFromRepository getEntityFromRepository;

    /**
     * Acessa uma conta bancaria existente.
     * @return Retorna os dados da conta bancaria encapsulados pelo DTO AccessAccountResponse.
     * @exception DisabledAccountException Lançada quando uma conta desabilitada tenta ser acessada.
     */
    public AccessAccountResponse execute() {

            // recupera os dados do cliente no banco de dados
        Client client = getEntityFromRepository.getClientById(decodeToken.execute(), clientRepository);

        // verifica se a conta está ativa
        if( client.getAccount().getStatus() == Status.DESABILITADA)
            throw new DisabledAccountException("A conta está DESABILITADA.");

        // retorna o dto de resposta
        return new AccessAccountResponse(client.getName().getValue(),
                client.getAccount().getAccountNumber().getValue(),
                client.getAccount().getBalance().getValue().toEngineeringString());
    }


    
}
