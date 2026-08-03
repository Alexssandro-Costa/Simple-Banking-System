package com.project.simple_banking_system.service.use_cases;


import com.project.simple_banking_system.service.auth.GetTokenData;
import com.project.simple_banking_system.utility.SearchEntityFromRepository;
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
import com.project.simple_banking_system.model.valueObjects.TransactionType;
import com.project.simple_banking_system.repository.AccountRepository;
import com.project.simple_banking_system.repository.ClientRepository;

/**
 * Classe de serviço que realiza uma transação bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 2.3
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
    private GetTokenData getTokenData;

    @Autowired
    private SearchEntityFromRepository searchEntityFromRepository;


    /**
     * Executa a operação de transação.
     * @param transactionRequest Requisição de transação.
     * @exception InvalidTransactionException Lançada quando uma transação falha
     * 
     */
    @Transactional
    public TransactionResponse execute(TransactionRequest transactionRequest) {

        // valida as informações inseridas;
        validate(transactionRequest);

        // Busca a conta do usuario atual
        Account userAccount = searchEntityFromRepository
                .getEntityById(getTokenData.getId(), clientRepository).getAccount();
    
        // cria uma transação
        Transaction transaction = new Transaction(transactionRequest);

        // realiza a transação baseada no tipo
        // TO DO: Substituir por um factory method
        if(transaction.getTransactionType() == TransactionType.TRANSFERENCIA) {

            // recupera a conta de destino da transferência
            Account accountReceiver = searchEntityFromRepository
                    .getAccountByAccountNumber(transaction.getReceiver(), accountRepository);

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
            // verifica se a string passada representa um enum valido
            TransactionType.valueOf(transactionRequest.transactionType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("O tipo de transação passado é invalido");
        }
        

    }

    
}
