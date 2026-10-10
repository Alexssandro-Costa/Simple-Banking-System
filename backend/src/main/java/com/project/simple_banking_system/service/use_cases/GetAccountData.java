package com.project.simple_banking_system.service.use_cases;


import com.project.simple_banking_system.service.auth.GetTokenData;
import com.project.simple_banking_system.service.util.SearchEntityFromRepository;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.DisabledAccountException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Response.AccountDataResponse;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.exceptions.AccountDataRequestFailedException;


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

    /**
     * Acessa uma conta bancaria existente.
     * @return Retorna os dados da conta bancaria encapsulados pelo DTO AccountDataResponse.
     * @exception DisabledAccountException Lançada quando uma conta desabilitada tenta ser acessada.
     */
    public AccountDataResponse execute() {

        try{
        // recupera os dados do cliente no banco de dados
        AccountDataResponse response = clientRepository.getAccountDataById(getTokenData.getId())
        .orElseThrow();

        if(response.name() == null || response.accountNumber() == null || response.name() == null)
            throw new NullElementException("Requisição retornou elementos nulos.");

        return response;

        }catch(Exception e) {
            throw new AccountDataRequestFailedException("Erro ao buscar dados da conta: " + e);
        }
       

    }


    
}
