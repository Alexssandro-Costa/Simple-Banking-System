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
                sender.getAccountNumber().toString(),
                receiver.getAccountNumber().toString());
    }



    @Test
    @DisplayName("deve lançar InvalidTransactionException quando o valor de transferencia for negativo")
    void performTransfer_ShouldFail_When_ValueIsNegative() {

        // Given
        sender.setBalance(new Cash(95D));
        receiver.setBalance(new Cash(0));

        transaction.setValue(new Cash(-10));

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
        sender.setBalance(new Cash(95D));
        receiver.setBalance(new Cash(0));

        transaction.setValue(new Cash(96));

        // When
        InvalidTransactionException exception = Assertions.assertThrows(InvalidTransactionException.class,
                () -> performTransfer.execute(sender, receiver, transaction));

        // then
        Assertions.assertEquals("Valor da transação, não pode ser maior que o saldo.", exception.getMessage());
    }


    @Test
    @DisplayName("Transferência deve falhar quando o saldo da conta remetente for nulo")
    void performTransfer_ShouldFail_When_SenderBalanceIsNull() {

        // given
        sender.setBalance(null);

        // when
        NullElementException exception = assertThrows(NullElementException.class,
                () -> performTransfer.execute(sender, receiver, transaction));

        // then
        assertEquals("Saldo do remetente não pode ser nulo.", exception.getMessage());
    }


    @Test
    @DisplayName("Transferência deve falhar quando o saldo da conta Destinataria for nulo")
    void performTransfer_ShouldFail_When_ReceiverBalanceIsNull() {

        // given
        receiver.setBalance(null);

        // when
        NullElementException exception = assertThrows(NullElementException.class,
                () -> performTransfer.execute(sender, receiver, transaction));

        // then
        assertEquals("Não foi possível localizar o saldo da conta destinatária.", exception.getMessage());
    }


    @Test
    @DisplayName("Transferência deve falhar quando o valor da transação for nulo")
    void performTransfer_ShouldFail_When_TransactionValueIsNyll() {

        // given
        transaction.setValue(null);

        // when
        NullElementException exception = assertThrows(NullElementException.class,
                () -> performTransfer.execute(sender, receiver, transaction));

        // then
        assertEquals("Valor da transação é não pode ser nulo.", exception.getMessage());
    }



    @Test
    @DisplayName("Deve transferir 20 e retornar um extrato com o novo saldo sendo 100")
    void performTransfer_When_TwentyIsTransferred_ItShouldReturn_ItShouldReturn_AStatementWithTheNewBalanceBeingOneHundred() {

        // Given
        sender.setBalance(new Cash(120D));
        sender.setTransactions(new ArrayList<Transaction>());

        receiver.setBalance(new Cash(0D));
        receiver.setTransactions(new ArrayList<Transaction>());

        transaction.setValue(new Cash(20D));
        transaction.setDate(Instant.now());

        // when
        TransactionResponse result = performTransfer.execute(sender, receiver, transaction);

        // then
        Assertions.assertEquals("100.0", result.newBalance());

    }

}