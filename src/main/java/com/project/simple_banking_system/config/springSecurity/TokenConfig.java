package com.project.simple_banking_system.config.springSecurity;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.simple_banking_system.exceptions.AuthenticationFailedException;
import com.project.simple_banking_system.model.entity.Client;


/**
 * Componente responsável pelo gerenciamento de tokens JWT (JSON Web Tokens).
 * <p>
 * Esta classe centraliza as operações de criação (geração) e decodificação (validação)
 * dos tokens de acesso utilizados para autenticar as requisições na API.
 * </p>
 *
 * @author Alexssandro
 * @version 1.0
 */
@Component
public class TokenConfig {

    /**
     * Chave secreta utilizada para assinar e garantir a integridade dos tokens.
     * <p>
     * <i>Nota de segurança:</i> Em ambiente de produção, esta chave deve ser armazenada
     * em variáveis de ambiente ou propriedades externas seguras, e não exposta diretamente no código.
     * </p>
     */
    private String secret = "secret";

    /**
     * Gera um token JWT para um cliente recém-autenticado.
     * <p>
     * O token armazena o UUID do cliente como uma Claim personalizada ("cliente-id"), o CPF como o Subject,
     * define a data de emissão (Issued At) e estabelece um tempo de expiração de 24 horas (86.400 segundos).
     * </p>
     *
     * @param client O objeto {@link Client} que encapsula as informações do usuário autenticado.
     * @return Uma {@link String} contendo o token JWT assinado no formato compactado.
     */
    public String generateToken(Client client) {

        // Define o algoritmo de assinatura utilizando a chave secreta
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                // Insere o UUID do cliente convertido em String na claim do token
                .withClaim("cliente-id", client.getId().toString())
                // Define o username (CPF) como a entidade principal (subject) do token
                .withSubject(client.getUsername())
                // Define o tempo de expiração do token (1 dia a partir do momento atual)
                .withExpiresAt(Instant.now().plusSeconds(86400))
                // Define o momento exato em que o token foi emitido
                .withIssuedAt(Instant.now())
                // Assina digitalmente o token com o algoritmo configurado
                .sign(algorithm);

    }

    /**
     * Valida a assinatura e decodifica os dados contidos em um token JWT recebido.
     * <p>
     * Tenta verificar o token de acordo com o algoritmo de assinatura configurado. Se o token for autêntico
     * e não estiver expirado, os dados são extraídos e empacotados em um objeto {@link JWTUserData}.
     * </p>
     *
     * @param token A String do token JWT (sem o prefixo "Bearer ").
     * @return Um {@link Optional} contendo o {@link JWTUserData} caso o token seja válido,
     * ou um {@link Optional#empty()} caso a validação falhe (assinatura alterada, expirado, etc).
     */
    public Optional<JWTUserData> validateToken(String token) {

        try {
            // Recria a chave com o algoritmo padrão para comparação
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Monta o verificador e executa a decodificação/validação do token recebido
            DecodedJWT decode = JWT.require(algorithm)
                    .build()
                    .verify(token);

            // Se nenhuma exceção for lançada, extrai as claims e retorna o record encapsulado
            return Optional.of(new JWTUserData(
                    decode.getClaim("cliente-id").asString(),
                    decode.getSubject()
            ));

        } catch (Exception e) {
            // Captura qualquer falha (TokenExpiredException, SignatureVerificationException) e retorna vazio de forma segura
            return Optional.empty();
        }
    
    }
}
