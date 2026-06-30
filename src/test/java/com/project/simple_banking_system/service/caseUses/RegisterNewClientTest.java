package com.project.simple_banking_system.service.caseUses;

import com.project.simple_banking_system.exceptions.InvalidDateException;
import com.project.simple_banking_system.exceptions.InvalidEnumValueException;
import com.project.simple_banking_system.exceptions.InvalidFormatException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Request.RegisterRequest;
import com.project.simple_banking_system.model.DTOs.Response.RegisterUserResponse;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.utility.ValidateData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RegisterNewClientTest {


    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ClientRepository clientRepository;

    @Spy
    ValidateData validateData;

    @Autowired
    @InjectMocks
    RegisterNewClient registerNewClient;


    @Test
    @DisplayName("Registro devo retornar um RegisterUserResponse contendo cpf equivalente")
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
    }

    @Test
    @DisplayName("Registro deve falhar quando a requisição de registro for nula")
    void registerNewClient_ShouldFail_When_RegisterRequestIsNull() {

        // given
        RegisterRequest registerRequest = null;

        // when
        NullElementException exception = Assertions.assertThrows(NullElementException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("Requisição de registro não pode ser nulo.", exception.getMessage());
    }

    //////////////////////////////////////// Name Test ///////////////////////////////////////


    @Test
    @DisplayName("Registro deve falhar quando o nome for nulo")
    void registerNewClient_ShouldFail_When_NameIsNull() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                null,
                "123.456.789-10",
                "MASCULINO",
                "22992684298",
                "2000-05-22",
                "Alex1234"
        );

        // when
        NullElementException exception = Assertions.assertThrows(NullElementException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("O Nome informado não pode ser nulo.", exception.getMessage());
    }


    @Test
    @DisplayName("Registro deve falhar quando o nome estiver em um formato não valido")
    void registerNewClient_ShouldFail_When_NameIsNonStandarlized() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alex123",
                "123.456.789-10",
                "MASCULINO",
                "22992684298",
                "2000-05-22",
                "Alex1234"
        );

        // when
        InvalidFormatException exception = Assertions.assertThrows(InvalidFormatException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("O nome informado está em um formato não valido.", exception.getMessage());
    }

    //////////////////////////////////////// CPF Test ///////////////////////////////////////


    @Test
    @DisplayName("Registro deve falhar quando o cpf for nulo")
    void registerNewClient_ShouldFail_When_CpfIsNull() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                null,
                "MASCULINO",
                "22992684298",
                "2000-05-22",
                "Alex1234"
        );

        // when
        NullElementException exception = Assertions.assertThrows(NullElementException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("O CPF informado não pode ser nulo.", exception.getMessage());
    }



    @Test
    @DisplayName("Registro deve falhar quando o cpf estiver fora do padrão")
    void registerNewClient_ShouldFail_When_CpfIsNonStandarlized() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123",
                "MASCULINO",
                "22992684298",
                "2000-05-22",
                "Alex1234"
        );

        // when
        InvalidFormatException exception = Assertions.assertThrows(InvalidFormatException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("O CPF informado está em um formato não valido.", exception.getMessage());
    }

    //////////////////////////////////////// DATEBIRTH Test ///////////////////////////////////////

    @Test
    @DisplayName("Registro deve falhar quando a data de nascimento for nula.")
    void registerNewClient_ShouldFail_When_DateBirthIsNull() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "MASCULINO",
                "22992684298",
                null,
                "Alex1234"
        );

        // when
        NullElementException exception = Assertions.assertThrows(NullElementException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("A data de nascimento não pode ser nula.", exception.getMessage());
    }


    @Test
    @DisplayName("Registro deve falhar quando a data de nascimento estiver no futuro")
    void registerNewClient_ShouldFail_When_DateBirthIsInFuture() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "MASCULINO",
                "22992684298",
                "2050-06-01",
                "Alex1234"
        );

        // when
        InvalidDateException exception = Assertions.assertThrows(InvalidDateException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("Data de nascimento não pode estar no futuro.", exception.getMessage());
    }


    @Test
    @DisplayName("Registro deve falhar quando o usuario for menor de idade")
    void registerNewClient_ShouldFail_When_UserIsMinor() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "MASCULINO",
                "22992684298",
                "2026-06-01",
                "Alex1234"
        );

        // when
        InvalidDateException exception = Assertions.assertThrows(InvalidDateException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("Usuario deve ter mais que 18 anos.", exception.getMessage());
    }

    //////////////////////////////////////// Gender Test ///////////////////////////////////////

    @Test
    @DisplayName("Registro deve falhar quando o genêro for nulo")
    void registerNewClient_ShouldFail_When_GenderIsNull() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                null,
                "22992684298",
                "2000-06-01",
                "Alex1234"
        );


        // when
        NullElementException exception = Assertions.assertThrows(NullElementException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("Genêro não pode ser nulo.", exception.getMessage());
    }


    @Test
    @DisplayName("Registro deve falhar quando o genêro informado não for valido.")
    void registerNewClient_ShouldFail_When_GenderIsNotValid() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "não valido",
                "22992684298",
                "2000-06-01",
                "Alex1234"
        );


        // when
        InvalidEnumValueException exception = Assertions.assertThrows(InvalidEnumValueException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("Genêro informado não é uma opção valida.", exception.getMessage());
    }

    //////////////////////////////////////// Phone Test ///////////////////////////////////////


    @Test
    @DisplayName("Registro deve falhar quando o Telefone for nulo.")
    void registerNewClient_ShouldFail_When_PhoneIsNull() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "masculino",
                null,
                "2000-06-01",
                "Alex1234"
        );


        // when
        NullElementException exception = Assertions.assertThrows(NullElementException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("O numero de telefone passado não pode ser nulo.", exception.getMessage());
    }



    @Test
    @DisplayName("Registro deve falhar quando o Telefone estiver fora do padrão ")
    void registerNewClient_ShouldFail_When_PhoneIsNonStandarlized() {

        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "masculino",
                "12",
                "2000-06-01",
                "Alex1234"
        );


        // when
        InvalidFormatException exception = Assertions.assertThrows(InvalidFormatException.class,
                () -> registerNewClient.execute(registerRequest));

        // then
        Assertions.assertEquals("Numero de telefone passado está em um formato não valido.", exception.getMessage());
    }

    //////////////////////////////////////// Password Test ///////////////////////////////////////

    @Test
    @DisplayName("Registro deve falhar quando a senha for nula")
    void registerNewClient_ShouldFail_When_PasswordIsNull() {
        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "masculino",
                "22992164321",
                "2000-06-01",
                null
        );

        // then
        NullElementException exception = Assertions.assertThrows(NullElementException.class,
                () -> registerNewClient.execute(registerRequest));

        // when
        Assertions.assertEquals("A senha informada não pode ser nula.", exception.getMessage());

    }


    @Test
    @DisplayName("Registro deve falhar quando a senhar estiver fora de um padrão aceito")
    void registerNewClient_ShouldFail_When_PasswordIsNonStandarlized() {
        // given
        RegisterRequest registerRequest = new RegisterRequest(
                "Alexssandro",
                "123.456.789-10",
                "masculino",
                "22992164321",
                "2000-06-01",
                "não padronizado"
        );

        // when
        InvalidFormatException exception = Assertions.assertThrows(InvalidFormatException.class,
                () -> registerNewClient.execute(registerRequest));

        // then

        Assertions.assertEquals("A senha passada está em um formato não valido.", exception.getMessage());
    }
}