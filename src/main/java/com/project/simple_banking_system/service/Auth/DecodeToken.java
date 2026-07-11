package com.project.simple_banking_system.service.Auth;


import com.project.simple_banking_system.config.springSecurity.JWTUserData;
import com.project.simple_banking_system.exceptions.TokenDecodificationFailedException;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Caso de uso/Serviço responsável por extrair e decodificar os dados do token de acesso do contexto atual.
 * <p>
 * Esta classe recupera o objeto de autenticação previamente validado pelo filtro de segurança
 * no {@link SecurityContextHolder} e extrai o identificador único do cliente logado.
 * </p>
 *
 * @author Alexssandro
 * @since release 3
 * @version 1.0
 */
@Service
public class DecodeToken {


    /**
     * Recupera o objeto principal autenticado no contexto de segurança e extrai o ID do usuário.
     * <p>
     * O método intercepta o contexto do Spring Security, verifica se o principal armazenado
     * é uma instância legítima de {@link JWTUserData} e converte a String do ID de volta para o formato {@link UUID}.
     * </p>
     *
     * @return O identificador único universal ({@link UUID}) do usuário autenticado no banco de dados.
     * @throws TokenDecodificationFailedException Lançada quando o contexto de segurança está vazio,
     * inválido ou o principal não corresponde ao tipo esperado.
     */
    public UUID execute() {

        try {
            // Obtém o objeto de maior privilégio (Principal) armazenado no contexto de segurança da requisição atual
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Verifica de forma segura (Pattern Matching do Java 14+) se o principal é do tipo JWTUserData
            if (principal instanceof JWTUserData userData) {
                // Converte a String do ID armazenada no Record para o tipo UUID nativo antes de retornar
                return UUID.fromString(userData.id());
            }

            // Força a ida para o bloco catch caso a estrutura do principal não seja a esperada
            throw new Exception("principal não é do tipo JWTUserData");

        } catch (Exception e) {
            // Encapsula qualquer erro de conversão ou de contexto na exceção de negócio personalizada
            throw new TokenDecodificationFailedException("Não foi possível decodificar o token de acesso.");
        }

    }
    
}
