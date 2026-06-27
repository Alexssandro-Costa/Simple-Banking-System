package com.project.simple_banking_system.service.caseUses;

import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;
import com.project.simple_banking_system.repository.AccountRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PerformTransferTest {


    private Account sender;
    private Account receiver;
    private Transaction transaction;

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    PerformTransfer performTransfer;


    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initTestingVariables() {

        sender = new Account();
        sender.setBalance(new Cash(0));

        receiver = new Account();
        receiver.setBalance(new Cash(0));

        transaction = new Transaction(new Cash(0),
                TransactionType.TRANSFERENCIA,
                null,
                null);
    }



    @Test
    @DisplayName("deve lançar InvalidTransactionException quando o valor de transferencia for negativo")
    void performTransfer_ShouldFail_When_ValueIsNegative() {

        // Given
        Account sender = new Account();
        sender.setBalance(new Cash(95D));

        Account receiver = new Account();
        receiver.setBalance(new Cash(0));

        Transaction transaction = new Transaction (
                new Cash(BigDecimal.valueOf(-10)),
                TransactionType.SAQUE,
                sender.getAccountNumber().toString(),
                receiver.getAccountNumber().toString()
        );


        // When
        InvalidTransactionException exception = Assertions.assertThrows(InvalidTransactionException.class,
                () -> performTransfer.execute(sender, receiver, transaction));


        // then
        Assertions.assertEquals("Valor da transação, não pode ser negativo.", exception.getMessage());
    }


    @Test
    @DisplayName("deve lançar InvalidTransactionException quando o valor de transferencia for maior que o saldo.")
    void performTransfer_ShouldFail_When_ValueIsGreaterThanTheBalance() {

        // Given
        Account sender = new Account();
        sender.setBalance(new Cash(95D));

        Account receiver = new Account();
        receiver.setBalance(new Cash(0));

        Transaction transaction = new Transaction (
                new Cash(BigDecimal.valueOf(96)),
                TransactionType.SAQUE,
                sender.getAccountNumber().toString(),
                receiver.getAccountNumber().toString()
        );

        // When
        InvalidTransactionException exception = Assertions.assertThrows(InvalidTransactionException.class,
                () -> performTransfer.execute(sender, receiver, transaction));

        // then
        Assertions.assertEquals("Valor da transação, não pode ser maior que o saldo.", exception.getMessage());
    }


    @Test
    @DisplayName("Deve transferir 20 e retornar um extrato com o novo saldo sendo 100")
    void performTransfer_When_TwentyIsTransferred_ItShouldReturn_ItShouldReturn_AStatementWithTheNewBalanceBeingOneHundred() {

        // Given
        Account sender = new Account();
        sender.setBalance(new Cash(120D));
        sender.setTransactions(new ArrayList<Transaction>());

        Account receiver = new Account();
        receiver.setBalance(new Cash(0D));
        receiver.setTransactions(new ArrayList<Transaction>());

        Transaction transaction = new Transaction (
                new Cash(BigDecimal.valueOf(20D)),
                TransactionType.TRANSFERENCIA,
                sender.getAccountNumber().toString(),
                receiver.getAccountNumber().toString()
        );
        transaction.setDate(Instant.now());

        // when
        TransactionResponse result = performTransfer.execute(sender, receiver, transaction);

        // then
        Assertions.assertEquals("100.0", result.newBalance());

    }

}