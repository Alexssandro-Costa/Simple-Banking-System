package com.project.simple_banking_system.service.caseUses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.project.simple_banking_system.config.springSecurity.TokenConfig;
import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.AuthenticationFailedException;
import com.project.simple_banking_system.exceptions.DisabledAccountException;
import com.project.simple_banking_system.model.DTOs.Request.LoginRequest;
import com.project.simple_banking_system.model.DTOs.Response.LoginResponse;
import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.model.valueObjects.Status;
import com.project.simple_banking_system.repository.ClientRepository;

@Service
public class AuthenticateClient {

    
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenConfig tokenConfig;


    public LoginResponse execute(LoginRequest loginRequest) {

        
        
        Client client = null;

        try {
            /// autentica a senha e o cpf passados
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.cpf(), loginRequest.password());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            client = (Client) authentication.getPrincipal();

        }catch(BadCredentialsException e) {
            throw new AuthenticationFailedException("Senha ou CPF Invalidos.");
        }
        catch (InternalAuthenticationServiceException e) {
            throw new AuthenticationFailedException("Senha ou CPF Invalidos.");
        }
        catch(Exception e) {
            throw new AuthenticationFailedException("Autenticação falhou.");
        }

        // verifica se a conta foi encontrada
        if(client == null)
            throw new AccountNotFoundException("Não foi possivel encontrar a conta buscada.");
        
        // verifica se a conta está DESABILITADA
        if(client.getAccount().getStatus() == Status.DESABILITADA) {
            throw new DisabledAccountException("A conta está DESABILITADA.");
        }

        String token = tokenConfig.generateToken(client);

        return new LoginResponse(token);
    }
    
}
