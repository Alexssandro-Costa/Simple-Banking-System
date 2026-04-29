package com.project.simple_banking_system.service.caseUses;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.InvalidEnumValueException;
import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Request.TransactionRequest;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;
import com.project.simple_banking_system.repository.AccountRepository;
import com.project.simple_banking_system.repository.ClientRepository;
import com.project.simple_banking_system.repository.TransactionRepository;

/**
 * Classe de serviço que realiza uma transação bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 2.0
 */
@Service
public class PerformTransaction {


    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DecodeToken decodeToken;


    /**
     * Executa a operação de transação.
     * @param accountNumber Numero da conta.
     * @param transactionDTO Requisição de transação.
     * @exception AccountNotFoundException Lançada quando uma conta bancaria não pode ser achada.
     * @exception InvalidTransactionException Lançada quando uma transação falha
     * 
     */
    @Transactional
    public TransactionResponse execute(TransactionRequest transactionRequest) {
        
        validate(transactionRequest);
        Account account;

        try {
            // busca a conta do client loggado
            account = clientRepository.findById(decodeToken.execute()).orElseThrow().getAccount();
        } catch (NoSuchElementException e) {
            throw new AccountNotFoundException("Não foi possivel encontrar a conta associada");
        }
    
        // cria uma nova entidade transaction
        Transaction transaction = new Transaction(
            new Cash(new BigDecimal(transactionRequest.value())), 
            TransactionType.valueOf(transactionRequest.transactionType().toUpperCase()), 
            new AccountNumber(transactionRequest.sender()), 
            new AccountNumber(transactionRequest.receiver()));
       
        if(transaction.getTransactionType() == TransactionType.TRANSFERENCIA) {

            if(transaction.getSender().getValue() == null || transaction.getReceiver().getValue() == null)
                throw new InvalidTransactionException("Não é possivel realizar uma transferência sem a conta remetente e destinataria.");

            // recupera a conta do destinatario
            Account receiver = accountRepository.findByAccountNumber(transaction.getReceiver());
            if(receiver == null)
                throw new AccountNotFoundException("Não foi possivel encontrar a conta destinataria");
    
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

        // salva a transação no banco e retorna-a.
        Transaction savedTransaction = transactionRepository.save(transaction);
        // salva a mudança no saldo
        accountRepository.save(account);
        
        // retorna um dto de resposta
        return new TransactionResponse(String.valueOf(savedTransaction.getId()), 
        savedTransaction.getTransactionType().name(), 
        savedTransaction.getValue().getValue().toEngineeringString(), 
        savedTransaction.getAccount().getBalance().getValue().toEngineeringString(), 
        savedTransaction.getReceiver().getValue(), 
        savedTransaction.getDate().toString());

    } 


    /**
     * Verifica se o numero da conta é a Requisição de transação são validos.
     * @param transactionRequest Requisição de transação.
     * @exception NullElementException Lançada quando um elemento informado é nulo.
     * @exception InvalidEnumValueException Lançada quando o tipo de transação passada, não corresponde a um enum valido
     */
    private void validate(TransactionRequest transactionRequest) {

        if(transactionRequest == null)
            throw new NullElementException("Transação passada é invalida.");
        if(transactionRequest.value() == null)
            throw new NullElementException("Valor de transação passado é invalido.");
        if(transactionRequest.transactionType() == null)
            throw new NullElementException("Tipo de transação passado é invalido.");

        try {
        
            // tenta converter a string para um enum.
            TransactionType.valueOf(transactionRequest.transactionType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("O tipo de transação passado é invalido");
        }
        

    }

    
}
