package com.project.simple_banking_system.service.use_cases;

import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;
import com.project.simple_banking_system.repository.AccountRepository;
import com.project.simple_banking_system.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;



@ExtendWith(MockitoExtension.class)
class PerformDepositTest {


    @Mock
    private AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Autowired
    @InjectMocks
    private PerformDeposit performDeposit;

    private void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    @DisplayName("Deposito deve falhar quando o valor for nagativo")
    void performDeposit_ShouldFail_When_ValueIsNegative() {

        //Given
        Account account = new Account();
        account.setBalance(new Cash(100));
        Transaction transaction = new Transaction(
                new Cash(BigDecimal.valueOf(-10)),
                TransactionType.DEPOSITO,
                "externo",
                account.getAccountNumber().toString()
        );
        //When
        InvalidTransactionException exception = Assertions.assertThrows(InvalidTransactionException.class,
                () -> performDeposit.execute(account, transaction)
        );

        // then
        // verifica se as mensagens de erro são iguais
        Assertions.assertEquals("Valor de deposito não pode ser negativo.", exception.getMessage());
    }


    @Test
    @DisplayName("Deve adicionar 100 ao saldo e retornar um extrato com o novo saldo sendo 120.")
    void performDeposit_When_OneHundredIsDepositedInBalance_ItShouldReturn_AStatementWithTheNewBalanceBeingOneHundredAndTwenty() {

        //Given
        Account account = new Account();
        account.setTransactions(new ArrayList<Transaction>());
        account.setBalance(new Cash(20));
        Transaction transaction = new Transaction(
                new Cash(BigDecimal.valueOf(100)),
                TransactionType.DEPOSITO,
                "externo",
                account.getAccountNumber().toString()
        );
        transaction.setDate(Instant.now());

        //When
        TransactionResponse result = performDeposit.execute(account, transaction);

        //Then
        Assertions.assertEquals("120", result.newBalance());

    }

    @Test
    @DisplayName("Deposito deve falhar quando a conta passado for nula.")
    void performDeposit_ShouldFail_When_TheAccountIsNull() {

        // Given
        Account account = null;
        Transaction transaction = new Transaction(
                new Cash(BigDecimal.valueOf(100)),
                TransactionType.DEPOSITO,
                "externo",
                null
        );
        transaction.setDate(Instant.now());

        // When

        NullElementException result = Assertions.assertThrows(NullElementException.class,
                () -> performDeposit.execute(account, transaction));


        // then
        Assertions.assertEquals("ERRO! Conta bancaria é invalida.",result.getMessage());

    }

    @Test
    @DisplayName("Deposito deve falhar quando o saldo da conta for nulo")
    void performDeposit_ShouldFail_When_TheAccountBalanceIsnull() {

        // given
        Account account = new Account();
        account.setBalance(null);
        account.setTransactions(new ArrayList<Transaction>());
        Transaction transaction = new Transaction(
                new Cash(BigDecimal.valueOf(100)),
                TransactionType.DEPOSITO,
                "externo",
                account.getAccountNumber().toString()
        );
        transaction.setDate(Instant.now());

        // when
        NullElementException result = Assertions.assertThrows(NullElementException.class,
                () -> performDeposit.execute(account, transaction));

        // then
        Assertions.assertEquals("Saldo bancario é invalido.", result.getMessage());
    }

    @Test
    @DisplayName("Deposito deve falhar quando o valor do saldo da conta for nulo")
    void performDeposit_ShouldFail_When_TheAccountBalanceValueIsnull() {

        // given
        Account account = new Account();
        account.setTransactions(new ArrayList<Transaction>());
        account.setBalance(new Cash());
        account.getBalance().setValue(null);

        Transaction transaction = new Transaction(
                new Cash(BigDecimal.valueOf(100)),
                TransactionType.DEPOSITO,
                "externo",
                account.getAccountNumber().toString()
        );
        transaction.setDate(Instant.now());

        // when
        NullElementException result = Assertions.assertThrows(NullElementException.class,
                () -> performDeposit.execute(account, transaction));

        // then
        Assertions.assertEquals("Saldo bancario é invalido.", result.getMessage());
    }


    @Test
    @DisplayName("Deposito deve falhar, quando o valor de transação for nulo.")
    void performDeposit_ShouldFail_When_TheTransactionValueIsNull() {

        //Given
        Account account = new Account();
        account.setTransactions(new ArrayList<Transaction>());
        account.setBalance(new Cash(20));

        Transaction transaction = new Transaction(
               null,
                TransactionType.DEPOSITO,
                "externo",
                account.getAccountNumber().toString()
        );
        transaction.setDate(Instant.now());

        // when
        NullElementException result = Assertions.assertThrows(NullElementException.class,
                () -> performDeposit.execute(account, transaction));

        // then
        Assertions.assertEquals("Valor de deposito é invalido.", result.getMessage());

    }



    @Test
    @DisplayName("Deposito deve falhar, quando o valor do valor de transação for nulo.")
    void performDeposit_ShouldFail_When_TheValueOfTransactionValueIsNull() {

        //Given
        Account account = new Account();
        account.setTransactions(new ArrayList<Transaction>());
        account.setBalance(new Cash(20));

        Transaction transaction = new Transaction(
                new Cash(),
                TransactionType.DEPOSITO,
                "externo",
                account.getAccountNumber().toString()
        );
        transaction.setValue(null);
        transaction.setDate(Instant.now());

        // when
        NullElementException result = Assertions.assertThrows(NullElementException.class,
                () -> performDeposit.execute(account, transaction));

        // then
        Assertions.assertEquals("Valor de deposito é invalido.", result.getMessage());

    }

}