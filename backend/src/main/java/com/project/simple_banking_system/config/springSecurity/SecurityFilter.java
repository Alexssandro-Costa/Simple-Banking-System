package com.project.simple_banking_system.config.springSecurity;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de segurança customizado que intercepta cada requisição HTTP recebida pela aplicação.
 * <p>
 * Herda de {@link OncePerRequestFilter} para garantir uma única execução por requisição.
 * O objetivo principal deste filtro é extrair o token JWT presente no cabeçalho 'Authorization',
 * validá-lo e, em caso de sucesso, autenticar o usuário no contexto do Spring Security.
 * </p>
 *
 * @author Alexssandro
 * @version 1.0
 */
@Component
public class SecurityFilter extends OncePerRequestFilter{

    /**
     * Componente responsável pelas operações de validação e geração de tokens JWT.
     */
    private final TokenConfig tokenConfig;

    /**
     * Construtor para injeção de dependência do serviço de configuração de tokens.
     *
     * @param tokenConfig Configurações e utilitários do token JWT.
     */
    public SecurityFilter(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }


    /**
     * Intercepta a requisição HTTP para processar a autenticação baseada em token Bearer JWT.
     * <p>
     * O método verifica a presença do cabeçalho 'Authorization'. Se o token for válido, os dados
     * do usuário (id e cpf) são encapsulados em um {@link UsernamePasswordAuthenticationToken} e
     * definidos no {@link SecurityContextHolder}. Por fim, a execução é repassada para o próximo filtro.
     * </p>
     *
     * @param request     O objeto {@link HttpServletRequest} contendo a requisição do cliente.
     * @param response    O objeto {@link HttpServletResponse} para gerenciar a resposta HTTP.
     * @param filterChain O objeto {@link FilterChain} para invocar o próximo filtro na cadeia.
     * @throws ServletException Caso ocorra um erro interno do servlet durante o processamento.
     * @throws IOException      Caso ocorra um erro de I/O ao manipular a requisição ou resposta.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera o cabeçalho de autorização da requisição HTTP
        String authorizedHeader = request.getHeader("Authorization");

        // Verifica se o cabeçalho está preenchido e se inicia com o prefixo padrão "Bearer "
        if (Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) {

            // Extrai apenas a string do token, removendo o prefixo "Bearer "
            String token = authorizedHeader.substring("Bearer ".length());

            // Solicita a validação do token ao componente TokenConfig
            Optional<JWTUserData> optUser = tokenConfig.validateToken(token);

            // Se o token for válido e contiver um usuário mapeado
            if (optUser.isPresent()) {
                JWTUserData userData = optUser.get();

                // Cria o objeto de autenticação do Spring Security usando os dados extraídos do JWT.
                // Passa 'null' para as credenciais (pois o token já foi validado) e uma lista vazia para Authorities (Roles).
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userData, null, Collections.emptyList());

                // Define a autenticação de forma global no contexto atual da requisição
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // Segue adiante na cadeia de filtros (cenário onde o cabeçalho existia)
            filterChain.doFilter(request, response);
        } else {
            // Segue adiante na cadeia de filtros (cenário onde o cabeçalho estava ausente ou era inválido)
            filterChain.doFilter(request, response);
        }
    }

    
}
