package com.project.simple_banking_system.service.use_cases;

import java.math.BigDecimal;

import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.repository.AccountRepository;
import com.project.simple_banking_system.repository.TransactionRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Valida e realiza um saque no saldo de uma conta bancaria
 * @author Alexssandro
 * @since release 3
 * @version 1.1
 */
@Service
@Transactional
public class PerformWithdraw {

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Executa a operação de saque.
     * @param account Conta bancaria relacionada.
     */
    protected TransactionResponse execute(Account account, @NonNull Transaction transaction) {

        // valida os dados inseridos
        validate(account, transaction.getValue());

        // atualiza o saldo
        Cash balance = account.getBalance();
        balance.subtract(transaction.getValue());
        account.setBalance(balance);

        // registra a transação no histórico da conta
        account.getTransactions().add(transaction);
        transaction.setAccount(account);

        // salva a operação no banco
        transactionRepository.save(transaction);



        // retorna um dto de resposta
        return new TransactionResponse(
                String.valueOf(transaction.getId()),
                transaction.getTransactionType().name(),
                transaction.getValue().toString(),
                transaction.getAccount().getBalance().toString(),
                transaction.getReceiver(),
                transaction.getDate().toString()
        );
    }


    /**
     * Verifica se a operação é valida.
     * @param account Conta bancaria associada.
     * @param withdrawValue - Valor de saque
     * @exception NullElementException Lançada quando um elemento é nulo.
     * @exception InvalidTransactionException Lançada quando uma operação não é possivel.
     */
    private void validate(Account account, Cash withdrawValue) {

        // conta bancaria é nula
        if(account == null)
            throw new NullElementException("Conta bancaria é invalida.");

        // saldo é nulo
        if(account.getBalance() == null || account.getBalance().getValue() == null)
            throw new NullElementException("Saldo bancario é invalido.");

        // valor de saque é nulo
        if(withdrawValue == null || withdrawValue.getValue() == null)
            throw new NullElementException("Valor de saque é invalido.");

        // valor de saque é negativo
        if(withdrawValue.getValue().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidTransactionException("O valor de saque não pode ser negativo."); 

        // valor de saque é maior que o saldo
        if(account.getBalance().getValue().compareTo(withdrawValue.getValue()) < 0)
            throw new InvalidTransactionException("Valor de saque não pode ser maior que o saldo.");

    }
    
}
