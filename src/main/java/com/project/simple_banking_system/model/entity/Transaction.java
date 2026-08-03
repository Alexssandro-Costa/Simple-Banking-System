package com.project.simple_banking_system.model.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.project.simple_banking_system.model.DTOs.Request.TransactionRequest;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * Entidade que armazena as transações realizadas.
 * @author Alexssandro
 * @since release 1
 * @version 1.1
 */
@Entity
@Table(name = "transacao")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @AttributeOverride(name = "value", column = @Column(name = "valor", nullable = false))
    @Embedded
    private Cash value;

    @Column(name = "data-emissao", nullable = false, updatable = false)
    @CreatedDate
    private Instant date;

    @Column(name = "tipo", nullable = false, length = 60)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "destinatario", nullable = true)
    private String receiver;

    @Column(name = "remetente", nullable = true)
    private String sender;

    /// DEFINE A CHAVE ESTRANGEIRA DE UMA TRANSAÇÃO
    @ManyToOne
    @JoinColumn(name = "conta-id", nullable = false)
    private Account account;

    public Transaction(Cash value, TransactionType transactionType, String sender, String receiver) {
        this.value = value;
        this.transactionType = transactionType;
        this.receiver = receiver;
        this.sender = sender;
    }

    public Transaction(TransactionRequest transactionRequest) {
        value = new Cash(transactionRequest.value());
        transactionType = TransactionType.valueOf(transactionRequest.transactionType().toUpperCase());
        receiver = transactionRequest.receiver().toUpperCase();
        sender = transactionRequest.sender().toUpperCase();

    }

    public Transaction() {
    }

    public UUID getId() {
        return id;
    }

    public Cash getValue() {
        return value;
    }

    public void setValue(Cash value) {
        this.value = value;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    } 

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
