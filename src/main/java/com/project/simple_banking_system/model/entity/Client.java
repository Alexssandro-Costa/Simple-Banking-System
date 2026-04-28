package com.project.simple_banking_system.model.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.simple_banking_system.model.DTOs.Request.RegisterRequest;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.valueObjects.DateBirth;
import com.project.simple_banking_system.model.valueObjects.Gender;
import com.project.simple_banking_system.model.valueObjects.Name;
import com.project.simple_banking_system.model.valueObjects.Password;
import com.project.simple_banking_system.model.valueObjects.Phone;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
public class Client extends Person implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @AttributeOverride(name = "value", column = @Column(name = "senha", nullable = false))
    @Embedded
    private Password password;
    
    // designa a uma relação bilateral com account
    @OneToOne(mappedBy = "client", cascade=CascadeType.ALL)
    private Account account;

    public Client(Name name, Cpf cpf, Gender gender, Phone phone, DateBirth dateBirth, Password password) {

        super(name, cpf, gender, phone, dateBirth);
        this.password = password;
        account = new Account();
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
        account.setClient(this);
    }


    @Override
    public @Nullable String getPassword() {
        return password.getValue();
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return getCpf().getValue();
    }
    


    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }


}
