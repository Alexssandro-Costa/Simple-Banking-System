package com.project.simple_banking_system.service.caseUses;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.InvalidEnumValueException;
import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.TransactionRequestDTO;
import com.project.simple_banking_system.model.DTOs.TranscationResponseDTO;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;
import com.project.simple_banking_system.repository.AccountRepository;
import com.project.simple_banking_system.repository.TransactionRepository;

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

    @Autowired
    TransactionRepository transactionRepository;


    /**
     * Executa a operação de transação.
     * @param accountNumber Numero da conta.
     * @param transactionDTO Requisição de transação.
     * @exception AccountNotFoundException Lançada quando uma conta bancaria não pode ser achada.
     * @exception InvalidTransactionException Lançada quando uma transação falha
     * 
     */
    @Transactional
    public TranscationResponseDTO execute(String accountNumber, TransactionRequestDTO transactionDTO) {
        
        validate(accountNumber, transactionDTO);

        // busca a conta do cliente logado.
        Account account = accountRepository.findByAccountNumber( new AccountNumber(accountNumber));
        if(account == null) 
            throw new AccountNotFoundException("Não foi possivel encontrar sua conta bancaria");
    

        Transaction transaction = new Transaction(
            new Cash(new BigDecimal(transactionDTO.value())), 
            TransactionType.valueOf(transactionDTO.transactionType()), 
            new AccountNumber(transactionDTO.sender()), 
            new AccountNumber(transactionDTO.receiver()));
       
        if(transaction.getTransactionType() == TransactionType.TRANSFERENCIA) {

            if(transaction.getSender().getValue() == null || transaction.getReceiver().getValue() == null)
                throw new InvalidTransactionException("Não é possivel realizar uma transferência sem a conta remetente e destinataria.");

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
        

        return new TranscationResponseDTO(String.valueOf(savedTransaction.getId()), 
        savedTransaction.getTransactionType().name(), 
        savedTransaction.getValue().toString(), 
        savedTransaction.getAccount().getBalance().getValue().toString(), 
        savedTransaction.getReceiver().getValue(), 
        savedTransaction.getDate().toString());

    } 


    /**
     * Verifica se o numero da conta é a Requisição de transação são validos.
     * @param accountNumberString Numero da conta.
     * @param transactionDTO Requisição de transação.
     * @exception NullElementException Lançada quando um elemento informado é nulo.
     */
    private void validate(String accountNumberString, TransactionRequestDTO transactionDTO) {
       

        if(accountNumberString == null)
            throw new NullElementException("Numero da conta passado é invalido.");

        if(transactionDTO == null)
            throw new NullElementException("Transação passada é invalida.");
        if(transactionDTO.value() == null)
            throw new NullElementException("Valor de transação passado é invalido.");
        if(transactionDTO.transactionType() == null)
            throw new NullElementException("Tipo de transação passado é invalido.");

        boolean transactionDTOValidation = Arrays.stream(TransactionType.values())
                       .anyMatch(c -> c.name().equals(transactionDTO.transactionType()));

        if(!transactionDTOValidation)
            throw new InvalidEnumValueException("O tipo de transação passado é invalido");
        

    }

    
}
