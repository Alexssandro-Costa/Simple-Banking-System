package com.project.simple_banking_system.model.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.project.simple_banking_system.model.DTOs.ClientDTO;
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

    public Client(ClientDTO clientDTO, Account account) {

        super(new Name(clientDTO.name()), new Cpf(clientDTO.cpf()), Gender.valueOf(clientDTO.gender()), new Phone(clientDTO.phone()), new DateBirth(LocalDate.parse(clientDTO.dateBirth())) );
        this.account = account;
        
    }


    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    


}
