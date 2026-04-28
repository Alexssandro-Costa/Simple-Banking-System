package com.project.simple_banking_system.service.caseUses;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.model.DTOs.Request.RegisterRequest;
import com.project.simple_banking_system.model.DTOs.Response.RegisterUserResponse;
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
 * Classe de serviço que cria uma nova entidade Cliente e Conta.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Service
public class RegisterNewClient {


    /// Inicializa automaticamente o repositorio. 
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional
    public RegisterUserResponse execute(RegisterRequest registerRequest) {

        // valida os dados passados
        ValidateData.execute(registerRequest);

        // inicializa uma nova entidade cliente
        Client client = new Client();

        // Define os dados do cliente
        client.setName(new Name(registerRequest.name().toUpperCase()));
        client.setCpf(new Cpf(registerRequest.cpf()));
        client.setGender(Gender.valueOf(registerRequest.gender()));
        client.setPhone(new Phone(registerRequest.phone()));
        client.setDateBirth(new DateBirth(LocalDate.parse(registerRequest.dateBirth())));
        client.setPassword(new Password(passwordEncoder.encode(registerRequest.password())));
        client.setAccount(new Account());

        // salva as entidades no banco de dados
        clientRepository.save(client);

        return new RegisterUserResponse(client.getUsername(), client.getPassword());
    }


    
}
