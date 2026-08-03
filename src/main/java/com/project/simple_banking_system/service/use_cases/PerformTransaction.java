package com.project.simple_banking_system.service.use_cases;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import com.project.simple_banking_system.service.auth.DecodeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

/**
 * Classe de serviço que realiza uma transação bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 2.1
 */
@Service
public class PerformTransaction {


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PerformDeposit performDeposit;
    @Autowired
    private PerformWithdraw performWithdraw;
    @Autowired
    private PerformTransfer performTransfer;

    @Autowired
    private DecodeToken decodeToken;


    /**
     * Executa a operação de transação.
     * @param transactionRequest Requisição de transação.
     * @exception AccountNotFoundException Lançada quando uma conta bancaria não pode ser achada.
     * @exception InvalidTransactionException Lançada quando uma transação falha
     * 
     */
    @Transactional
    public TransactionResponse execute(TransactionRequest transactionRequest) {

        // valida as informações inseridas;
        validate(transactionRequest);
        Account userAccount;

        try {
            // busca a conta do client loggado
            userAccount = clientRepository.findById(decodeToken.execute()).orElseThrow().getAccount();
        } catch (NoSuchElementException e) {
            throw new AccountNotFoundException("Não foi possivel encontrar a conta associada");
        }
    
        // cria uma nova entidade transaction
        Transaction transaction = new Transaction(
            new Cash(new BigDecimal(transactionRequest.value())), 
            TransactionType.valueOf(transactionRequest.transactionType().toUpperCase()), 
            transactionRequest.sender().toUpperCase(),
            transactionRequest.receiver().toUpperCase());
       
        if(transaction.getTransactionType() == TransactionType.TRANSFERENCIA) {

            Account accountReceiver;
            try {
                // recupera a conta de destino da transferência
                accountReceiver = accountRepository.findByAccountNumber(new AccountNumber(transaction.getReceiver())).orElseThrow();
            } catch (NoSuchElementException e) {
                throw new AccountNotFoundException("Não foi possivel encontrar a conta destinataria.");
            }
            return performTransfer.execute(userAccount, accountReceiver, transaction);

        }
        else if(transaction.getTransactionType() == TransactionType.DEPOSITO) {
            return performDeposit.execute(userAccount, transaction);

        }
        else if(transaction.getTransactionType() == TransactionType.SAQUE) {
            return performWithdraw.execute(userAccount, transaction);
        }
        else {
            throw new InvalidEnumValueException("Tipo de transação invalido.");
        }


    } 


    /**
     * Verifica se o número da conta e a Requisição de transação são validos.
     * @param transactionRequest Requisição de transação.
     * @exception NullElementException Lançada quando um elemento informado é nulo.
     * @exception InvalidEnumValueException Lançada quando o tipo de transação passada, não corresponde a um enum valido
     */
    private void validate(TransactionRequest transactionRequest) {

        if(transactionRequest == null)
            throw new NullElementException("Transação não pode ser nula.");
        if(transactionRequest.value() == null)
            throw new NullElementException("Valor da transação não pode ser nula.");
        if(transactionRequest.transactionType() == null)
            throw new NullElementException("Tipo de transação não pode ser nula.");
        if(transactionRequest.receiver() == null)
            throw new NullElementException("Destinatário não pode ser nulo.");
        if(transactionRequest.sender() == null)
            throw  new NullElementException("Remetente não pode ser nulo.");

        try {
            // tenta converter a string para um enum.
            TransactionType.valueOf(transactionRequest.transactionType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("O tipo de transação passado é invalido");
        }
        

    }

    
}
