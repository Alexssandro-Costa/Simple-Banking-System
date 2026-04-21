package com.project.simple_banking_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;


/**
 * Repositorio que manipula dados de uma conta bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>{

    /**
     * Procura uma conta bancaria pelo numero da conta.
     * @param accountNumber Numero da conta bancaria.
     * @return uma conta bancaria.
     */
    public Account findByAccountNumber(AccountNumber accountNumber);

    
}
