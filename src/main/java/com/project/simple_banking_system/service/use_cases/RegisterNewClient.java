package com.project.simple_banking_system.service.use_cases;

import java.time.LocalDate;

import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Request.AuthenticationRequest;
import com.project.simple_banking_system.model.DTOs.Response.AuthenticationResponse;
import com.project.simple_banking_system.service.auth.AuthenticateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.model.DTOs.Request.RegisterRequest;
import com.project.simple_banking_system.model.DTOs.Response.RegisterResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.valueObjects.DateBirth;
import com.project.simple_banking_system.model.valueObjects.Gender;
import com.project.simple_banking_system.model.valueObjects.Name;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.model.valueObjects.Phone;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.utility.ValidateData;


/**
 * Classe de serviço que cria uma entidade Cliente.
 * @author Alexssandro
 * @since release 3
 * @version 2.1
 */
@Service
public class RegisterNewClient {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidateData validateData;

    @Autowired
    private AuthenticateClient authenticateClient;


    /**
     * Registra um novo cliente no banco de dados.
     * @param registerRequest Requisição de registro contêndo os dados do cliente.
     * @return Retorna o Username e o Password do usuario encapsulados pelo DTO RegisterUserResponse. 
     */
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {

        // valida os dados passados
        validate(registerRequest);

        // inicializa uma nova entidade cliente
        Client client = new Client();

        // Define os dados do cliente
        client.setName(new Name(registerRequest.name()
                .toUpperCase())
        );

        client.setCpf(new Cpf(registerRequest.cpf()));

        client.setGender(Gender.valueOf(registerRequest
                .gender()
                .toUpperCase())
        );

        client.setPhone(new Phone(registerRequest.phone()));

        client.setDateBirth(new DateBirth(LocalDate.parse(registerRequest.dateBirth())));

        client.setPassword(new Password(passwordEncoder.encode(registerRequest.password())));

        client.setAccount(new Account());

        // salva as entidades no banco de dados
        clientRepository.save(client);

        // Realiza a autenticação do usuário e retorna um dto
        return new RegisterResponse(
                authenticateClient.execute(
                        new AuthenticationRequest(
                                registerRequest.cpf(),
                                registerRequest.password()
                        )
                )
        );
    }


    /**
     * Valida os dados da requisição de registro.
     * @param registerRequest Requisição de registro.
     * @exception NullElementException Lançada quando a requisição de registro é nula.
     */
    private void validate(RegisterRequest registerRequest) {

        if(registerRequest == null)
            throw new NullElementException("Requisição de registro não pode ser nulo.");

        validateData.validateName(registerRequest.name());
        validateData.validateCpf(registerRequest.cpf());
        validateData.validateDateBirth(registerRequest.dateBirth());
        validateData.validateGender(registerRequest.gender());
        validateData.validatePhone(registerRequest.phone());
        validateData.validatePassword(registerRequest.password());

    }


    
}
