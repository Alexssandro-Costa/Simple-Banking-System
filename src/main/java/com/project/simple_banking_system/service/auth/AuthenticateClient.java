package com.project.simple_banking_system.service.auth;

import com.project.simple_banking_system.exceptions.EntityNotFoundException;
import com.project.simple_banking_system.model.DTOs.Response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.project.simple_banking_system.config.springSecurity.TokenConfig;
import com.project.simple_banking_system.exceptions.AuthenticationFailedException;
import com.project.simple_banking_system.model.DTOs.Request.AuthenticationRequest;
import com.project.simple_banking_system.model.entity.Client;



/**
 * Caso de uso/Serviço responsável por realizar a autenticação dos clientes no sistema.
 * <p>
 * Esta classe valida as credenciais enviadas (CPF e senha) utilizando o gerenciador do Spring Security
 * e, após a confirmação da identidade, delega a criação do token de acesso JWT.
 * </p>
 * @author Alexssandro
 * @since release 3
 * @version 1.0
 */
@Service
public class AuthenticateClient {

    /**
     * Gerenciador de autenticação nativo do Spring Security configurado para validar as credenciais.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Componente de configuração responsável por gerar e gerenciar o ciclo de vida dos tokens JWT.
     */
    @Autowired
    private TokenConfig tokenConfig;

    /**
     * Executa o processo de autenticação do cliente com base nas credenciais fornecidas.
     * <p>
     * O método encapsula os dados em um token não autenticado, solicita a validação ao
     * {@link AuthenticationManager}, recupera a entidade do cliente logado e gera o token de acesso.
     * </p>
     *
     * @param authenticationRequest Objeto DTO contendo as credenciais de login (CPF e senha) enviadas pelo cliente.
     * @return Um {@link AuthenticationResponse} contendo o token JWT gerado com sucesso.
     * @throws AuthenticationFailedException Caso a senha/CPF estejam incorretos ou ocorra um erro no processo.
     * @throws EntityNotFoundException  Caso o principal retornado não corresponda a uma conta ativa ou existente.
     */
    public AuthenticationResponse execute(AuthenticationRequest authenticationRequest) {
        Client client;

        try {
            // Cria um objeto de autenticação não verificado contendo as credenciais fornecidas (CPF e Senha)
             UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                     new UsernamePasswordAuthenticationToken(
                             authenticationRequest.cpf(), authenticationRequest.password()
                     );

            // Delega ao gerenciador do Spring Security a validação do hash da senha e busca do usuário
            Authentication authentication = authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);

            // Recupera o objeto do usuário autenticado (Principal) e realiza o cast para a entidade Client do domínio
            client = (Client) authentication.getPrincipal();

        } catch (BadCredentialsException e) {
            // Captura explicitamente o erro de credenciais incorretas (senha errada ou CPF inexistente)
            throw new AuthenticationFailedException("Senha ou CPF Inválidos.");
        } catch (Exception e) {
            // Captura qualquer outra falha genérica no ecossistema de autenticação
            throw new AuthenticationFailedException("Autenticação falhou.");
        }

        // Verifica defensivamente se o objeto client foi devidamente preenchido pelo fluxo anterior
        if (client == null) {
            throw new EntityNotFoundException("Não foi possível encontrar a conta buscada.");
        }

        // Gera o token de acesso JWT de curta duração com base nos dados consolidados do cliente
        String token = tokenConfig.generateToken(client);

        // Retorna o DTO de resposta encapsulando a String do token para o Controller
        return new AuthenticationResponse(token);
    }
}
