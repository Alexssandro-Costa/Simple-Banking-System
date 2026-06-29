package com.project.simple_banking_system.service.caseUses;

import com.project.simple_banking_system.model.DTOs.Request.RegisterRequest;
import com.project.simple_banking_system.model.DTOs.Response.RegisterUserResponse;
import com.project.simple_banking_system.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RegisterNewClientTest {


    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ClientRepository clientRepository;

    @Autowired
    @InjectMocks
    RegisterNewClient registerNewClient;

    private void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void registerNewClient_ShouldReturn_ARegisterUserResponseWithEqualCpf() {

        // Given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "MASCULINO",
                "22992684298",
                "2000-05-22",
                "Alex1234"
        );
        RegisterUserResponse expected = new RegisterUserResponse(registerRequest.cpf(),
                passwordEncoder.encode(registerRequest.password()));

        // When
        RegisterUserResponse result = registerNewClient.execute(registerRequest);

        // Then
        Assertions.assertEquals(expected.cpf(), result.cpf(), "CPF esperado, não é compatível com o resultado");
        System.out.println(expected + ";" + result);
    }
}