package com.project.simple_banking_system.service.auth;

import com.project.simple_banking_system.config.springSecurity.TokenConfig;
import com.project.simple_banking_system.model.DTOs.Request.AuthenticationRequest;
import com.project.simple_banking_system.model.DTOs.Response.AuthenticationResponse;
import com.project.simple_banking_system.model.entity.Client;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticateClientTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenConfig tokenConfig;

    @Mock
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    @Mock
    private Authentication authentication;

    @Mock
    private Client client;

    @Autowired
    @InjectMocks
    private AuthenticateClient authenticateClient;

    @BeforeEach
    void setup() {

        // configura o retorno de authenticationManager
        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // configura o retorno de authentication
        when(authentication.getPrincipal())
                .thenReturn(client);


        when(tokenConfig.generateToken(any(Client.class)))
                .thenReturn("jwt-token");

    }

    @Test
    @DisplayName("Autenticação deve retornar um AuthenticationResponse")
    void Authentication_ShouldReturn_AuthenticationResponse_withNonNullToken() {
        // given

        String cpf = "111.111.111-11";
        String password = "Pedro1234";
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(cpf, password);

        // when
        AuthenticationResponse authenticationResponse = authenticateClient.execute(authenticationRequest);
        AuthenticationResponse expectedResponse = new AuthenticationResponse("jwt-token");

        // then
        Assertions.assertEquals(expectedResponse, authenticationResponse);
        Assertions.assertNotNull(authenticationResponse.token());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication).getPrincipal();
        verify(tokenConfig).generateToken(any(Client.class));

    }
}