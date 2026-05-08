package com.project.simple_banking_system.service.caseUses;

import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;
import com.project.simple_banking_system.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PerformWithdrawTest {


    @Mock
    private AccountRepository accountRepository;

    @Autowired
    @InjectMocks
    PerformWithdraw performWithdraw;

    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Saque deve falhar quando o valor passado for negativo")
    void performWithdraw_ShouldFail_When_ValueIsNegative() {

        //Given
        Account account = new Account();
        account.setBalance(new Cash(BigDecimal.valueOf(100)));
        Transaction transaction = new Transaction (
                new Cash(BigDecimal.valueOf(-10)),
                TransactionType.SAQUE,
                account.getAccountNumber().toString(),
                "externo"
        );

        // When
        InvalidTransactionException exception = Assertions.assertThrows(InvalidTransactionException.class,
                () -> performWithdraw.execute(account, transaction)
        );

        // then
        Assertions.assertEquals("O valor de saque não pode ser negativo.", exception.getMessage());
    }


    @Test
    @DisplayName("Saque deve falhar quando o valor de passado for maior que o saldo")
    void performWithdraw_ShouldFail_When_WithdrawAmmountIsGreaterThanTheBalance() {

        //Given
        Account account = new Account();
        account.setBalance(new Cash(BigDecimal.valueOf(10)));
        Transaction transaction = new Transaction (
                new Cash(BigDecimal.valueOf(20)),
                TransactionType.SAQUE,
                account.getAccountNumber().toString(),
                "externo"
        );

        // When
        InvalidTransactionException exception = Assertions.assertThrows(InvalidTransactionException.class,
                () -> performWithdraw.execute(account, transaction)
        );

        // then
        Assertions.assertEquals("Valor de saque não pode ser maior que o saldo.", exception.getMessage());
    }


    @Test
    @DisplayName("Deve retirar 80 do saldo e retornar um extrato com o novo saldo sendo 20.")
    void performWithdraw_When_EightyIsWithdrawnFromTheBalance_ItShouldReturnAStatementWithTheNewBalanceBeingTwenty() {

        //Given
        Account account = new Account();
        account.setBalance(new Cash(BigDecimal.valueOf(100)));
        account.setTransactions(new ArrayList<Transaction>());

        Transaction transaction = new Transaction (
                new Cash(BigDecimal.valueOf(80)),
                TransactionType.SAQUE,
                account.getAccountNumber().toString(),
                "externo"
        );
        transaction.setDate(Instant.now());

        //When
        TransactionResponse result = performWithdraw.execute(account, transaction);


        //Then
        Assertions.assertEquals("20", result.newBalance());
    }



}