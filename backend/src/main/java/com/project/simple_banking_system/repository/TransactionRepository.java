package com.project.simple_banking_system.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.simple_banking_system.model.entity.Transaction;

/**
 * Repositorio que manipula dados de transações bancarias.
 * @author Alexssandro
 * @since release 3
 * @version 1.1
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    /**
     * Busca as transações relacionadas ao id da conta passado.
     * @param accountId id da conta que as transações estão relacionadas
     * @param pageable elemento de paginação contendo o número da pagina e a quantidade de item que devem ser buscados
     * @return Retorna uma coleção paginada de transações relacionadas ao id passado
     */
    public Page<Transaction> findAllByAccountId(UUID accountId, Pageable pageable);
}