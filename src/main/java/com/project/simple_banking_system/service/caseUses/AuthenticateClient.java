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
import com.project.simple_banking_system.model.DTOs.Request.LoginRequest;
import com.project.simple_banking_system.model.DTOs.Response.LoginResponse;
import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.repository.ClientRepository;


/**
 * Autentica as credenciais do usuario.
 * @author Alexssandro
 * @since release 3
 * @version 1.0
 */
@Service
public class AuthenticateClient {

    
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenConfig tokenConfig;


    /**
     * Autentica as credênciais do usuario.
     * @param loginRequest Requisição de login contêndo o userName e o Password. 
     * @return Retorna um token JWT encapsulado pelo DTO LoginResponse.
     * @exception AuthenticationFailedException Lançada quando a autênticação não pode ser realizada.
     * @exception AccountNotFoundException Lançada quando não pode ser encontrada uma conta relacionada.
     */
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

        String token = tokenConfig.generateToken(client);

        return new LoginResponse(token);
    }
    
}
