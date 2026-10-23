package com.project.simple_banking_system.service.use_cases;

import java.util.List;

import com.project.simple_banking_system.model.DTOs.Request.CheckStatementRequest;
import com.project.simple_banking_system.model.DTOs.Response.TransactionDTO;
import com.project.simple_banking_system.repository.TransactionRepository;
import com.project.simple_banking_system.service.auth.TokenData;
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
    private TokenData tokenData;

    /**
     * Executa uma busca paginada das transações referentes a uma conta bancaria.
     * @param pageNumber número da página buscada na paginação
     * @param itemQuantity quantidade de itens que devem ser buscados por página
     * @return CheckStatementResponse contendo uma lista de transações e informações da paginação
     */
  public CheckStatementResponse execute(int pageNumber, int itemQuantity) {

      // recupera uma paginação das transações
      Page<Transaction> transactions =
              transactionRepository.findAllByAccountId(tokenData.getAccountId(),
                      PageRequest.of(
                              pageNumber,
                              itemQuantity,
                              Sort.by("date").descending()
                      ));

      // cria um dto das transações associadas a uma conta contendo apenas informações essenciais
      List<TransactionDTO> transactionsDTO = transactions.stream()
              .map(TransactionDTO::new)
              .toList();

      return new CheckStatementResponse(transactionsDTO, transactions.getTotalPages(),
              transactions.isFirst(), transactions.isLast());

  }
}
