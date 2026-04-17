package com.project.simple_banking_system.model.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.project.simple_banking_system.model.DTOs.ClientDTO;
import com.project.simple_banking_system.model.DTOs.RegisterRequestDTO;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.valueObjects.DateBirth;
import com.project.simple_banking_system.model.valueObjects.Gender;
import com.project.simple_banking_system.model.valueObjects.Name;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.model.valueObjects.Phone;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entidade que armazena as informações de um cliente.
 * @author Alexssandro
 * @since release 1
 * @version 2
 */
@Entity
@Table(name = "cliente")
public class Client extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    // designa a uma relação bilateral com account
    @OneToOne(mappedBy = "client", cascade=CascadeType.ALL)
    private Account account;

    public Client(Name name, Cpf cpf, Gender gender, Phone phone, DateBirth dateBirth, Password password) {

        super(name, cpf, gender, phone, dateBirth);
        account = new Account(password);
    }

    public Client(RegisterRequestDTO registerRequest) {

        super(new Name(registerRequest.name()), new Cpf(registerRequest.cpf()), Gender.valueOf(registerRequest.gender()), new Phone(registerRequest.phone()), new DateBirth(LocalDate.parse(registerRequest.dateBirth())) );
        
    }

    public Client() {}


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    


}
