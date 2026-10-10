package com.project.simple_banking_system.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.project.simple_banking_system.model.entity.Client;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.model.DTOs.Response.AccountDataResponse;

/**
 * Repositorio que manipula dados de um cliente.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    public Optional<UserDetails> findByCpf(Cpf cpf);


    
    @Query("""
            SELECT new com.project.simple_banking_system.model.DTOs.Response.AccountDataResponse(
                c.name,
                a.accountNumber,
                a.balance
            ) 
            FROM Client c
            INNER JOIN c.account a
            WHERE c.id = :id
            """)
    public Optional<AccountDataResponse> getAccountDataById(@Param("id") UUID id);
}
