package com.project.simple_banking_system.service.use_cases;

import com.project.simple_banking_system.exceptions.InvalidTransactionException;
import com.project.simple_banking_system.exceptions.NullElementException;
import com.project.simple_banking_system.model.DTOs.Response.TransactionResponse;
import com.project.simple_banking_system.model.entity.Account;
import com.project.simple_banking_system.model.entity.Transaction;
import com.project.simple_banking_system.model.valueObjects.Cash;
import com.project.simple_banking_system.model.valueObjects.TransactionType;
import com.project.simple_banking_system.repository.AccountRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PerformWithdrawTest {

    private Account account;
    private Transaction transaction;

    @Mock
    private AccountRepository accountRepository;

    @Autowired
    @InjectMocks
    PerformWithdraw performWithdraw;

    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initTestingVariables() {

        account = new Account();
        transaction = new Transaction(
                new Cash(0),
                TransactionType.SAQUE,
                null,
                null
        );
    }



    @Test
    @DisplayName("Saque deve falhar quando a conta for nula")
    void PerformWithdraw_ShouldFail_When_AccountIsNull() {

        // given
        account = null;

        // when
        NullElementException exception = assertThrows(NullElementException.class,
                () -> performWithdraw.execute(account, transaction)
        );

        // then
        assertEquals("Conta bancaria é invalida.", exception.getMessage());

    }

    @Test
    @DisplayName("saque deve falhar quando o saldo da conta for nulo.")
    void performWithdraw_ShouldFail_when_AccountBalanceIsNull() {

        // given
        account.setBalance(null);

        // when
        NullElementException exception = assertThrows(NullElementException.class,
                () -> performWithdraw.execute(account, transaction));

        assertEquals("Saldo bancario é invalido.", exception.getMessage());
    }


    @Test
    @DisplayName("Saque deve falhar quando o valor de saque for nulo")
    void performWithdraw_ShouldFail_When_WithdrawValueIsNull() {

        // given
        account.setBalance(new Cash(100));
        transaction.setValue(null);

        // when
        NullElementException exception = assertThrows(NullElementException.class,
                () -> performWithdraw.execute(account, transaction));

        //then
        assertEquals("Valor de saque é invalido.", exception.getMessage());
    }

    @Test
    @DisplayName("Saque deve falhar quando o valor passado for negativo")
    void performWithdraw_ShouldFail_When_ValueIsNegative() {

        //Given
        account.setBalance(new Cash(0));
        transaction.setValue(new Cash(-10));

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
        account.setBalance(new Cash(10));
        transaction.setValue(new Cash(20));

        // When
        InvalidTransactionException exception = Assertions.assertThrows(InvalidTransactionException.class,
                () -> performWithdraw.execute(account, transaction)
        );

        // then
        Assertions.assertEquals("Valor de saque não pode ser maior que o saldo.", exception.getMessage());
    }


    @Test
    @DisplayName("Deve retirar 80 do saldo e retornar um extrato com o novo saldo sendo 20.")
    void performWithdraw_When_EightyIsWithdrawnFromTheBalance_ItShouldReturn_AStatementWithTheNewBalanceBeingTwenty() {

        //Given
        account.setBalance(new Cash(100));
        account.setTransactions(new ArrayList<Transaction>());

        transaction.setValue(new Cash(80));
        transaction.setSender(account.getAccountNumber().toString());
        transaction.setReceiver("externo");
        transaction.setDate(Instant.now());

        //When
        TransactionResponse result = performWithdraw.execute(account, transaction);


        //Then
        Assertions.assertEquals("20", result.newBalance());
    }



}