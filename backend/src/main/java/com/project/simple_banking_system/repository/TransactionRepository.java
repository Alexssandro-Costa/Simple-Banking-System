package com.project.simple_banking_system.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.project.simple_banking_system.model.DTOs.Response.statistics.GenericDataResponse;
import com.project.simple_banking_system.model.DTOs.Response.statistics.TransactionDataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.simple_banking_system.model.entity.Transaction;

/**
 * Repositorio que manipula dados de transações bancarias.
 * @author Alexssandro
 * @since release 3
 * @version 1.2
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    /**
     * Busca as transações relacionadas ao id da conta passado.
     * @param accountId id da conta que as transações estão relacionadas
     * @param pageable elemento de paginação contendo o número da pagina e totalVolume quantidade de item que devem ser buscados
     * @return Retorna uma coleção paginada de transações relacionadas ao id passado
     */
    public Page<Transaction> findAllByAccountId(UUID accountId, Pageable pageable);

    /**
     * Busca as transações relacionadas ao id da conta bancaria passada
     * @param accountId id da conta bancaria
     * @return retorna uma lista das transações relacionadas ao id passado
     */
    public List<Transaction> findAllByAccountId(UUID accountId);

    /**
     * Retorna dados estatísticos genéricos de transações relacionadas a uma conta
     * @param accountId id de uma conta bancaria especifica
     * @return uma lista contendo os dados estatísticos filtrados
     */
    @Query("""
    SELECT new com.project.simple_banking_system.model.DTOs.Response.statistics.TransactionDataResponse(
        t.transactionType,
        COUNT(t),
        SUM(t.value.value),
        AVG(t.value.value)
    )
    FROM Transaction t
    WHERE t.account.id = :accountId
    GROUP BY t.transactionType
""")
    public Optional< List<TransactionDataResponse> > getGenericTransactionsStatisticsByAccountId(@Param("accountId") UUID accountId);
}