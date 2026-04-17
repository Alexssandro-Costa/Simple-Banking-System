package com.project.simple_banking_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simple_banking_system.model.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>{
    
}
