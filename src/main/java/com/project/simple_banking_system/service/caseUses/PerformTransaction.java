package com.project.simple_banking_system.service.caseUses;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.model.DTOs.TransactionRequestDTO;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;
import com.project.simple_banking_system.repository.AccountRepository;

/**
 * Classe de serviço que realiza uma transação bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Service
public class PerformTransaction {


    @Autowired
    AccountRepository accountRepository;


    @Transactional
    public void execute(String accountNumber, TransactionRequestDTO transactionDTO) {
        
        Account account = accountRepository.findByAccountNumber( new AccountNumber(accountNumber));

        Transaction transaction = new Transaction(
            new Cash(new BigDecimal(transactionDTO.value())), 
            TransactionType.valueOf(transactionDTO.transactionType()), 
            new AccountNumber(transactionDTO.sender()), 
            new AccountNumber(transactionDTO.receiver()));
       
        if(transaction.getTransactionType() == TransactionType.TRANSFERENCIA) {

            // verificar se sender e receiver não são nulos

            Account receiver = accountRepository.findByAccountNumber(transaction.getReceiver());
            PerformTransfer.execute(receiver, account, transaction);

            Transaction receiverTransaction = new Transaction(transaction.getValue(), 
            transaction.getTransactionType(), 
            transaction.getSender(), 
            transaction.getReceiver());

            // define as dependencias de receiver
            receiverTransaction.setAccount(receiver);
            receiver.getTransactions().add(receiverTransaction);

            // salva a mudança no saldo e a nova transação
            accountRepository.save(receiver);

        }
        else if(transaction.getTransactionType() == TransactionType.DEPOSITO) {
            PerformDeposit.execute(account, transaction.getValue());

        }
        else if(transaction.getTransactionType() == TransactionType.SAQUE) {
            PerformWithdraw.execute(account, transaction.getValue());
        }

         // define as dependepencias
        transaction.setAccount(account);
        account.getTransactions().add(transaction);

        // salva a mudança no saldo
        accountRepository.save(account);

    } 

    
}
