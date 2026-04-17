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
import com.project.simple_banking_system.repository.TransactionRepository;

@Service
public class PerformTransaction {


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Transactional
    public void execute(String accountNumber, TransactionRequestDTO transactionDTO) {
        
        Account account = accountRepository.findByAccountNumber( new AccountNumber(accountNumber));

        Transaction transaction = new Transaction(new Cash(new BigDecimal(transactionDTO.value())), 
        TransactionType.valueOf(transactionDTO.transactionType()), 
        new AccountNumber(transactionDTO.sender()), 
        new AccountNumber(transactionDTO.receiver()));
       
        if(transaction.getTransactionType() == TransactionType.TRANSFERENCIA) {

            // verificar se sender e receiver não são nulos

            Account receiver = accountRepository.findByAccountNumber(transaction.getReceiver());

            account.setBalance(PerformWithdraw.execute(account.getBalance(), transaction.getValue()));
            receiver.setBalance(PerformDeposit.execute(receiver.getBalance(), transaction.getValue()));

            Transaction receiverTransaction = new Transaction(transaction.getValue(), transaction.getTransactionType(), transaction.getSender(), transaction.getReceiver());

            // define as dependencias
            receiverTransaction.setAccount(receiver);
            receiver.getTransactions().add(receiverTransaction);

            // salva a mudança no saldo
            accountRepository.save(receiver);

            // salva uma nova transação no banco de dados
            transactionRepository.save(receiverTransaction);

        }
        else if(transaction.getTransactionType() == TransactionType.DEPOSITO) {
            
            account.setBalance( PerformDeposit.execute(account.getBalance(), transaction.getValue()) );

        }
        else if(transaction.getTransactionType() == TransactionType.SAQUE) {

            account.setBalance(PerformWithdraw.execute(account.getBalance(), transaction.getValue()));
        }

         // define as dependepencias
        transaction.setAccount(account);
        account.getTransactions().add(transaction);

        // salva a mudança no saldo
        accountRepository.save(account);

        // salva uma nova transação no banco de dados
        transactionRepository.save(transaction);



    } 

    
}
