package com.bankingSystem.service;

import com.bankingSystem.repository.dao.ClientDAO;
import com.bankingSystem.dto.AccountDTO;
import com.bankingSystem.exceptions.*;
import com.bankingSystem.model.CPF;
import com.bankingSystem.model.Client;
import com.bankingSystem.model.Password;
import com.bankingSystem.exceptions.*;
import com.bankingSystem.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import static com.bankingSystem.utility.Generator.generateNumericString;


/**
 * Classe de serviço que implementa métodos de acesso, criação e exclusão de contas e,
 * operações Bancarias.
 * @author Alexssandro
 * @since release 1
 * @version 2.2
 */
public class ClientService implements IUserAccount<AccountDTO>, IOperations<AccountDTO> {

    private final ClientDAO cDao;

    public ClientService() {
        cDao = new ClientDAO();
    }

    /**
     * Realiza um acesso a conta bancaria especifica.
     * @param accNumber Numero da conta
     * @param password Senha
     * @return AccountDTO Informações essências da conta.
     * @exception AuthenticationFailedException Ocorre quando autenticação falha.
     */
    @Override
    public AccountDTO login(String accNumber, Password password) {

        if(accNumber == null || password == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = cDao.getClientByAccountNumber(accNumber);

            // Faz a autenticação da senha passada
            authentication(client.getPassword(), password);

            return new AccountDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new AuthenticationFailedException(e.getMessage());
        }

    }

    /**
     * Cria uma conta bancaria.
     * @param name Nome do titular
     * @param cpf CPF dp titular
     * @param phoneNumber Numero de telefone
     * @param DtBirth Data de nascimento
     * @param password Senha
     * @return AccountDTO Informações essências da conta.
     * @exception RegistrationFailedException Ocorre quando a criação da conta falha
     */
    @Override
    public AccountDTO register(String name, CPF cpf, String phoneNumber, LocalDate DtBirth, Password password) {

        try {
            Client client = new Client(name, cpf, phoneNumber, DtBirth, password, generateNumericString(9), BigDecimal.ZERO);

            cDao.insertClient(client); // insere a entidade no banco de dados

            // retorna um DTO com os dados principais
            return new AccountDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new RegistrationFailedException(e.getMessage());
        }
    }

    /**
     * Exclui a conta bancaria associada.
     * @param accNumber Numero da conta
     * @param cpf CPF do titular
     * @param password Senha
     * @exception DeletationFailedException Ocorre quando a exclusão falha.
     */
    @Override
    public void deleteAccount(String accNumber, CPF cpf, Password password) {

        try{

            Client client = cDao.getClientByAccountNumber(accNumber);  // busca a conta no banco de dados

            // Faz a autenticação da senha passada
            authentication(client.getPassword(), password);

            cDao.deleteClient(accNumber, cpf); // Deleta a conta do cliente

        }catch (Exception e) {
            throw new DeletationFailedException(e.getMessage());
        }
    }

    /**
     * Realiza uma retira no saldo de uma conta bancaria.
     * @param accNumber Numero da conta.
     * @param password Senha.
     * @param value Valor de retirada.
     * @return AccountDTO Informações essências da conta.
     * @exception InputException Ocorre quando uma entrada é nula.
     * @exception InvalidWithdrawException - Ocorre quando um saque falha.
     */
    @Override
    public AccountDTO withdraw(String accNumber, Password password, BigDecimal value) {

        if(accNumber == null || password == null || value == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = cDao.getClientByAccountNumber(accNumber); // busca a conta no banco de dados

            // Faz a autenticação da senha passada
            authentication(client.getPassword(), password);

            client.getAccount().withdraw(value); // Realiza uma retirada de valor da conta
            cDao.updateBalance(accNumber, client.getAccount().getBalance());  // salva a operação no banco

            // retorna um DTO com os dados principais
            return new AccountDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new InvalidWithdrawException(e.getMessage());
        }
    }

    /**
     * Realiza um depósito numa conta bancaria.
     * @param accNumber Numero da conta.
     * @param password Senha.
     * @param value Valor de deposito.
     * @return AccountDTO Informações essências da conta.
     * @exception InputException Ocorre quando uma entrada é nula.
     * @exception InvalidDepositException - Ocorre quando o deposito falha.
     */
    @Override
    public AccountDTO deposit(String accNumber, Password password, BigDecimal value) {

        if(accNumber == null || password == null || value == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = cDao.getClientByAccountNumber(accNumber); // busca a conta no banco

            // Faz a autenticação da senha passada
            authentication(client.getPassword(), password);

            client.getAccount().deposit(value); // Realiza um depósito de valor da conta
            cDao.updateBalance(accNumber, client.getAccount().getBalance()); // Salva no banco

            return new AccountDTO (client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        }catch (Exception e) {
            throw new InvalidDepositException(e.getMessage());
        }

    }

    /**
     * Realiza uma transferência de valor entre duas contas bancarias.
     * @param accNumber Numero da conta remetente.
     * @param accountTarget Numero da conta destinatária.
     * @param password Senha.
     * @param value Valor de transferência.
     * @return AccountDTO Informações essências da conta.
     * @exception InputException Ocorre quando uma entrada é nula.
     * @exception InvalidTransferException - Ocorre quando a transferência falha.
     */
    @Override
    public AccountDTO transfer(String accNumber, String accountTarget, Password password, BigDecimal value) {

        if(accNumber == null || password == null || accountTarget == null || value == null)
            throw new InputException("Elemento invalido informado");


        try{
            // acessa as contas
            Client sender = cDao.getClientByAccountNumber(accNumber);
            Client receiver = cDao.getClientByAccountNumber(accountTarget);

            // Faz a autenticação da senha passada
            authentication(sender.getPassword(), password);

            sender.getAccount().withdraw(value); // Realiza uma retirada de valor da conta remetente
            receiver.getAccount().deposit(value); // Realiza uma inserção de valor na conta destinataria

            // atualiza as contas bancarias
            cDao.updateBalance(accNumber, sender.getAccount().getBalance());
            cDao.updateBalance(accountTarget, receiver.getAccount().getBalance());

            return new AccountDTO(sender.getName(), sender.getAccount().getAccountNumber(),
                    sender.getAccount().getBalance().toString());
        }catch (Exception e) {
            throw new InvalidTransferException(e.getMessage());
        }

    }

}
