package com.project.simple_banking_system.service.caseUses;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.DisabledAccountException;

import com.project.simple_banking_system.model.DTOs.Response.AccessAccountResponse;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.model.entity.Client;


/**
 * Classe de serviço que garante acesso a uma conta bancaria existente.
 * @author Alexssandro
 * @since release 3
 * @version 2.0
 */
@Service
public class AccessAccount {


    // inicializa automaticamente o repositorio. 
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DecodeToken decodeToken;



    /**
     * Acessa uma conta bancaria existente.
     * @return Retorna os dados da conta bancaria encapsulados pelo DTO AccessAccountResponse.
     * @exception DisabledAccountException Lançada quando uma conta disabilitada tenta ser acessada.
     * @exception AccountNotFoundException Lançada quando uma conta não pode ser encontrada.
     */
    public AccessAccountResponse execute() {

        try {
            // recupera os dados do cliente no banco de dados
            Client client = clientRepository.findById(decodeToken.execute()).orElseThrow();

            // verifica se a conta está ativa
            if( client.getAccount().getStatus() == Status.DESABILITADA) {
                throw new DisabledAccountException("A conta está DESABILITADA.");
            }

            // retorna o dto de resposta
            return new AccessAccountResponse(client.getName().getValue(), 
            client.getAccount().getAccountNumber().getValue(), 
            client.getAccount().getBalance().getValue().toEngineeringString());

        }catch(NoSuchElementException e) {
            throw new AccountNotFoundException("Não foi possivel encontrar a conta associada.");

        }
    }


    
}
