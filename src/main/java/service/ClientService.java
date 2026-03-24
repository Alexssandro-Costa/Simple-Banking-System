package service;

import DTO.ClientDTO;
import exceptions.*;
import model.Account;
import model.CPF;
import model.Client;
import model.Password;

import java.math.BigDecimal;
import java.time.LocalDate;

import static utility.Generator.generateNumericString;

public class ClientService {

    private DataService dt;

    public ClientService() {
        dt = new DataService();
    }

    public ClientDTO login(String accNumber, Password password) {

        /// confirma se todos os dados passados são validos e se forem, se conecta a conta relacionada;

        if(accNumber == null || password == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = dt.select(accNumber, password.getValue());

            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new AccountNotFoundException(e.getMessage());
        }

    }

    public ClientDTO Register(String name, CPF cpf, String phoneNumber, LocalDate DtBirth, Password password) {


        /// Registra a conta de um cliente ao banco

        Client client = new Client(name, cpf, phoneNumber, DtBirth, password, generateNumericString(9), BigDecimal.ZERO);

        // Insere a conta valida no banco de dados
        try {
            dt.insert(client);
            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new AccountNotFoundException(e.getMessage());
        }
    }

    public ClientDTO performWithdraw(String accNumber, Password password, BigDecimal value) {

        /// realiza uma retirada de valor de uma conta associada

        if(accNumber == null || password == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = dt.select(accNumber, password.getValue()); // busca a conta no banco de dados
            client.getAccount().withdraw(value);//verifica se o saque é valido
            dt.update(client.getAccount().getAccountNumber(),value.negate()); // realiza o saque no banco de dados

            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new InvalidWithdrawException(e.getMessage());
        }
    }

    public ClientDTO performDeposit(String accNumber, Password password, BigDecimal value) {

        /// realiza um incremento de valor de uma conta associada

        if(accNumber == null || password == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = dt.select(accNumber, password.getValue()); // busca a conta no banco
            client.getAccount().deposit(value); // verifica se o deposito é valido
            dt.update(client.getAccount().getAccountNumber(), value); // realiza o deposito no banco de dados

            return new ClientDTO (client.getName(), client.getAccount().getAccountNumber(), client.getAccount().getBalance().toString());
        }catch (Exception e) {
            throw new InvalidDepositException(e.getMessage());
        }

    }

    public ClientDTO transfer(String accNumber, Password password, String accountTarget, BigDecimal value) {

        /// Realiza uma transferencia de de saldo entre a conta conectada e a conta destinataria

        if(accNumber == null || password == null || accountTarget == null)
            throw new InputException("Elemento invalido informado");


        try{
            Client client = dt.select(accNumber, password.getValue()); // busca a conta no banco
            client.getAccount().withdraw(value); // verifica se é valido
            dt.update(client.getAccount().getAccountNumber(), accountTarget, value); // realiza a transferência no banco de dados
            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(), client.getAccount().getBalance().toString());
        }catch (Exception e) {
            throw new InvalidTransferException(e.getMessage());
        }

    }

}
