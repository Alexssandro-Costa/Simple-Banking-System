package com.project.simple_banking_system.service.caseUses;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Response.CheckStatementResponse;
import com.project.simple_banking_system.model.entity.Client;
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

    // inicializa automaticamente
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DecodeToken decodeToken;
  
    /**
    * Executa a busca do extratos bancario de uma conta.
    * @return TransactionsDTO - Uma lista das transações realizadas pela conta.
    * @exception AccountNotFoundException Lançada quando uma conta não pode ser encontrada.
    * @exception NullElementException Lançada quando um elemento é nulo.
    */
  public List<CheckStatementResponse> execute() {

    try {

      // autentica e recupera o cliente 
      Client client = clientRepository.findById(decodeToken.execute()).orElseThrow();

      // recupera todas as transações realizadas pela conta
      List<Transaction> transactions = client.getAccount().getTransactions();
      List<CheckStatementResponse> checkStatementResponseList = new ArrayList<CheckStatementResponse>();


      // transforma as transações em DTOs
      for(int index = 0; index < transactions.size(); index ++) {

        checkStatementResponseList.add( new CheckStatementResponse(
          transactions.get(index).getId().toString(), 
          transactions.get(index).getTransactionType().name(),
          transactions.get(index).getValue().getValue().toEngineeringString(),
          String.valueOf(transactions.get(index).getReceiver()),
          transactions.get(index).getDate().toString()
        ));
      }


      return checkStatementResponseList;
        
      } catch (NoSuchElementException e) {
        throw new AccountNotFoundException("Não foi possivel encontrar a conta associada");
      }

    }
}
