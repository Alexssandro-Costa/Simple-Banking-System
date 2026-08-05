package com.project.simple_banking_system.service.use_cases;


import com.project.simple_banking_system.service.auth.GetTokenData;
import com.project.simple_banking_system.service.util.SearchEntityFromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.DisabledAccountException;

import com.project.simple_banking_system.model.DTOs.Response.AccountDataResponse;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.model.entity.Client;


/**
 * Classe de serviço que recupera os dados de uma conta bancaria existente.
 * @author Alexssandro
 * @since release 3
 * @version 2.1
 */
@Service
public class GetAccountData {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GetTokenData getTokenData;

    @Autowired
    private SearchEntityFromRepository searchEntityFromRepository;

    /**
     * Acessa uma conta bancaria existente.
     * @return Retorna os dados da conta bancaria encapsulados pelo DTO AccountDataResponse.
     * @exception DisabledAccountException Lançada quando uma conta desabilitada tenta ser acessada.
     */
    public AccountDataResponse execute() {

        // recupera os dados do cliente no banco de dados
        Client client = searchEntityFromRepository.getEntityById(getTokenData.getId(), clientRepository);

        // verifica se a conta está ativa
        if( client.getAccount().getStatus() == Status.DESABILITADA)
            throw new DisabledAccountException("ERRO. Não é possível acessar uma conta desativada.");

        // retorna o dto de resposta
        return new AccountDataResponse(
                client.getName().getValue(),
                client.getAccount().getAccountNumber().getValue(),
                client.getAccount().getBalance().getValue().toEngineeringString()
        );
    }


    
}
