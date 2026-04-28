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


@Component
public class TokenConfig {
    
    private String secret = "secret";

    public String generateToken(Client client) {

        Algorithm algorithm = Algorithm.HMAC256(secret);


        return JWT.create()
            .withClaim("cliente-id", client.getId().toString())
            .withSubject(client.getUsername())
            .withExpiresAt(Instant.now().plusSeconds(86400))
            .withIssuedAt(Instant.now())
            .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {


        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(algorithm)
                .build().verify(token);

            return Optional.of(new JWTUserData(decode.getClaim("cliente-id").asString(), decode.getSubject()));

        }catch(Exception e) {
            return Optional.empty();
        }
    
    }
}
