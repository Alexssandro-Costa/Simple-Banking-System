package service;

import exceptions.InvalidPasswordException;
import model.Account;
import model.Bank;
import model.Client;
import exceptions.AccountNotFoundException;
import utility.Generator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClientService {

    private Client client;
    private DataService dt;

    public ClientService() {
        dt = new DataService();
    }

    public Client login(String cpf, String password) {

        // confirma se todos os dados passados são validos e se forem, se conecta a conta relacionada;

        Client element = dt.getClient(cpf, password);

        if(cpf == null)
            throw new NullPointerException("O CPF passado não é valido!");
        if(password == null)
            throw new NullPointerException("A senha passada não é valida");
        if(element == null)
            throw new AccountNotFoundException("Não foi possivel encontrar a conta buscada");
        if(!element.getPassword().compare(password))
            throw new InvalidPasswordException("Senha ou CPF invalido");

        return client = element;

    }

    public Client Register(String name, String cpf, String phoneNumber, LocalDate DtBirth, String password) throws SQLException {


        // Registra a conta de um cliente ao banco

        client = new Client(name, cpf, phoneNumber, DtBirth, password,
                new Account(Generator.generateNumericString(9), BigDecimal.ZERO));

        dt.setClient(client);

        return client;
    }

    public void performWithdraw(BigDecimal value) {

        // realiza uma retirada de valor de uma conta associada

        if(client == null)
            throw new NullPointerException("Não foi possivel estabelecer uma conexão");

        client.getAccount().withdraw(value);

    }

    public void performDeposit(BigDecimal value) {

        // realiza um incremento de valor de uma conta associada

        if(client == null)
            throw new NullPointerException("Não foi possivel estabelecer uma conexão");

        client.getAccount().deposit(value);

    }

    public void transfer(String accountTarget, BigDecimal value) {

        // Realiza uma transferencia bancaria entre a conta logada é a conta alvo

    }

}
