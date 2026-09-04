package com.project.simple_banking_system.model.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.model.valueObjects.Status;


/**
 * Entidade que armazena as informações de uma conta bancaria.
 * @author Alexssandro
 * @since release 1
 * @version 3
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "conta")
public class Account{

    @Schema(description = "ID da entidade conta")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(description = "Número da conta", example = "012345678")
    @AttributeOverride(name = "value", column = @Column(name = "numero-conta", nullable = false, unique=true))
    @Embedded
    private final AccountNumber accountNumber;

    @Schema(description = "Saldo da conta", example = "1000.00")
    @AttributeOverride(name = "value", column = @Column(name = "balanco", nullable = false))
    @Embedded
    private Cash balance;

    @Schema(description = "Status da conta", examples = {"HABILITADA", "DESABILITADA"})
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Schema(description = "designa a relação de chave estrangeira com a entidade client")
    @OneToOne
    @JoinColumn(name = "cliente-id")
    private Client client;

    @Schema(description = "designa a relação de chave estrangeira com a entidade transactions")
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;


    public Account() {
        this.accountNumber = new AccountNumber();
        balance = new Cash(0);
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
