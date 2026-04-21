package com.project.simple_banking_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.simple_banking_system.model.entity.Transaction;

/**
 * Repositorio que manipula dados de transações bancarias.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID>{
    
}
