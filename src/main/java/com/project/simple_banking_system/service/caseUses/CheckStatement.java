package com.project.simple_banking_system.service.caseUses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.model.DTOs.TransactionDTO;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.AccountNumber;
import com.project.simple_banking_system.repository.AccountRepository;

/**
 * Classe de serviço que busca todo o extrato de uma conta bancaria.
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
@Service
public class CheckStatement {

  // inicializa automaticamnete
  @Autowired
  AccountRepository accountRepository;
    

  /**
  * Executa a busca do extratos bancario de uma conta.
  * @param accountNumberString Numero da conta bancaria relacionada.
  * @return TransactionsDTO - Uma lista das transações realizadas pela conta.
  */
  public List<TransactionDTO> execute(String accountNumberString) {

    Account acc = accountRepository.findByAccountNumber(new AccountNumber(accountNumberString));

    List<Transaction> Acctransactions = acc.getTransactions();

    List<TransactionDTO> transactionsDTO = new ArrayList<TransactionDTO>();


    // transforma as transações em DTOs
    for(int index = 0; index < Acctransactions.size(); index ++) {

      transactionsDTO.add(Acctransactions.get(index).toDTO());
    }


    return transactionsDTO;

  }
}
