package com.project.simple_banking_system.model.entity;

import java.util.ArrayList;
import java.util.UUID;

import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.model.valueObjects.Status;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


/**
 * Entidade que armazena as informações de uma conta bancaria.
 * @author Alexssandro
 * @since release 1
 * @version 3
 */
@Entity
@Table(name = "conta")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @AttributeOverride(name = "value", column = @Column(name = "numero-conta", nullable = false, unique=true))
    @Embedded
    private AccountNumber accountNumber;

    @AttributeOverride(name = "value", column = @Column(name = "balanco", nullable = false))
    @Embedded
    private Cash balance;

    @AttributeOverride(name = "value", column = @Column(name = "senha", nullable = false))
    @Embedded
    private Password password;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    // designa a relação de chave estrangeira com cliente
    @OneToOne
    @JoinColumn(name = "cliente-id")
    private Client client;

    // designa a relação com transactions
    @OneToMany(mappedBy = "account")
    private ArrayList<Transaction> transactions;


    public Account(Password password) {
        this.accountNumber = new AccountNumber();
        this.password = password;
        balance = new Cash();
        status = Status.HABILITADA;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }


    public Cash getBalance() {
        return balance;
    }

    public void setBalance(Cash balance) {
        this.balance = balance;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }


    
}
