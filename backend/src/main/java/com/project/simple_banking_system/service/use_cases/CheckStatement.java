package com.project.simple_banking_system.service.use_cases;

import java.util.List;

import com.project.simple_banking_system.model.DTOs.Request.CheckStatementRequest;
import com.project.simple_banking_system.model.DTOs.Response.TransactionDTO;
import com.project.simple_banking_system.repository.TransactionRepository;
import com.project.simple_banking_system.service.auth.GetTokenData;
import com.project.simple_banking_system.service.util.SearchEntityFromRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
  public CheckStatementResponse execute(CheckStatementRequest checkStatementRequest) {

      // recupera uma paginação das transações
      Page<Transaction> transactions =
              transactionRepository.findAllByAccountId(getTokenData.getAccountId(),
                      PageRequest.of(
                              checkStatementRequest.pageNumber(),
                              checkStatementRequest.quantity(),
                              Sort.by("date").descending()
                      ));

      // cria um dto das transações associadas a conta contendo apenas informações essenciais
      List<TransactionDTO> transactionsDTO = transactions.stream()
              .map(TransactionDTO::new)
              .toList();

      return new CheckStatementResponse(transactionsDTO, transactions.getTotalPages(),
              transactions.isFirst(), transactions.isLast());

  }
}
