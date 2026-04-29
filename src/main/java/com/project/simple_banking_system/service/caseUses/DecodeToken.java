package com.project.simple_banking_system.service.caseUses;


import com.project.simple_banking_system.config.springSecurity.JWTUserData;
import com.project.simple_banking_system.exceptions.TokenDecodificationFailedException;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * Decodifica um token de acesso recebido.
 * @author Alexssandro
 * @since release 3
 * @version 1.0
 */
@Service
public class DecodeToken {


    /**
     * Decodifica um token de acesso e recupera o id do usuario.
     * @return Identificador do usuario no banco de dados.
     * @exception TokenDecodificationFailedException Lançada quando a decodificação de um token falha.
     */
    protected UUID execute() {

        try {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // retorna o id presente no token de acesso
            if(principal instanceof JWTUserData userData) {
                return UUID.fromString(userData.id());
            }

            throw new Exception("principal não é do tipo JWTUserData");

        }catch (Exception e) {
            throw new TokenDecodificationFailedException("Não foi possivel decodificar o token de acesso.");
        }

    }
    
}
