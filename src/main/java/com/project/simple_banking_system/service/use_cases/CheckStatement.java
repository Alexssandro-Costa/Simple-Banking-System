package com.project.simple_banking_system.service.use_cases;

import java.util.List;

import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.repository.TransactionRepository;
import com.project.simple_banking_system.service.auth.GetTokenData;
import com.project.simple_banking_system.service.util.SearchEntityFromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.model.DTOs.Response.CheckStatementResponse;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.repository.ClientRepository;

/**
 * Classe de serviço que busca todo o extrato de uma conta bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 2.0
 */
@Service
public class CheckStatement {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SearchEntityFromRepository searchEntityFromRepository;

    @Autowired
    private GetTokenData getTokenData;
  
    /**
    * Executa a busca do extratos bancario de uma conta.
    * @return TransactionsDTO - Uma lista das transações realizadas pela conta.
    */
  public List<CheckStatementResponse> execute() {

      // recupera a conta do usuário atual
      Account userAccount = searchEntityFromRepository.
              getEntityById(getTokenData.getId(), clientRepository).getAccount();

      List<Transaction> transactions = userAccount.getTransactions();

      // retorna um dto das transações associadas a conta
      return transactions
              .stream()
              .map(transaction -> new CheckStatementResponse(
                      transaction.getId(),
                      transaction.getTransactionType(),
                      transaction.getValue(),
                      transaction.getReceiver(),
                      transaction.getDate()
              )).toList();
  }
}
