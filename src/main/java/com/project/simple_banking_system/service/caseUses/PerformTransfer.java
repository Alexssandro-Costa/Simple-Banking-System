package com.project.simple_banking_system.service.caseUses;

import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.repository.AccountRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * Valida e realiza uma transferência entre duas contas bancarias.
 * @author Alexssandro
 * @since release 3
 * @version 1.1
 */
@Service
@Transactional
public class PerformTransfer {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Executa a operação de transferência.
     * @param receiver Conta Destinatária do valor.
     * @param sender Conta Remetente do valor.
     * @param transaction Transação que está sendo realizada.
     */
    public TransactionResponse execute(@NonNull Account sender, @NonNull Account receiver, @NonNull Transaction transaction) {

        // valida os dados de entrada
        validate(sender, receiver, transaction);

        // atualiza os saldos das contas
        sender.getBalance().subtract(transaction.getValue());
        receiver.getBalance().add(transaction.getValue());

        // cria a transação da classe destinatária.
        Transaction receiverTransaction = new Transaction(transaction.getValue(),
                transaction.getTransactionType(),
                transaction.getSender(),
                transaction.getReceiver());

        // registra a transação no histórico das contas
        sender.getTransactions().add(transaction);
        transaction.setAccount(sender);

        receiver.getTransactions().add(receiverTransaction);
        receiverTransaction.setAccount(receiver);

        // salva a operaçãp no banco de dados
        accountRepository.save(sender);
        accountRepository.save(receiver);


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
     * @param sender Conta bancaria associada ao remetente.
     * @param receiver Conta bancaria associada ao destinatário.
     * @param transaction Transação que está sendo realizada.
     * @exception NullElementException Lançada quando um elemento é nulo.
     * @exception InvalidTransactionException Lançada quando uma operação não é possível.
     */
    private void validate( Account sender, Account receiver, Transaction transaction) {

        // verificação básica
        if(sender.getBalance() == null || sender.getBalance().getValue() == null)
            throw  new NullElementException("Saldo do remetente não pode ser nulo.");
        else if(receiver.getBalance() == null || receiver.getBalance().getValue() == null)
            throw new NullElementException("Não foi possível localizar o saldo da conta destinatária.");
        else if(transaction.getValue() == null || transaction.getValue().getValue() == null)
            throw new NullElementException("Valor da transação é não pode ser nulo.");

        // regras de negócio
        if(transaction.getValue().getValue().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidTransactionException("Valor da transação, não pode ser negativo.");
        if(sender.getBalance().getValue().compareTo(transaction.getValue().getValue()) < 0)
            throw new InvalidTransactionException("Valor da transação, não pode ser maior que o saldo.");

    }
}
