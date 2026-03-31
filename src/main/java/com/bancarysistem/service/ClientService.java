package com.bancarysistem.service;

import com.bancarysistem.DAO.ClientDAO;
import com.bancarysistem.DTO.ClientDTO;
import com.bancarysistem.exceptions.*;
import com.bancarysistem.model.CPF;
import com.bancarysistem.model.Client;
import com.bancarysistem.model.Password;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.bancarysistem.utility.Generator.generateNumericString;

public class ClientService {

    private final ClientDAO cDao;

    public ClientService() {
        cDao = new ClientDAO();
    }

    public ClientDTO login(String accNumber, Password password) {

         /*
            confirma se todos os dados passados são validos e se forem, se conecta a conta relacionada;
            @return retorna um DTO com os dados principais do cliente.
         */

        if(accNumber == null || password == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = cDao.getClientByAccountNumber(accNumber);

            if(!Password.compare(client.getPassword().getValue(), password.getValue()))
                throw new AccessDeniedException("Não foi permitido o acesso a conta.");

            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new AuthenticationFailedException(e.getMessage());
        }

    }

    public ClientDTO Register(String name, String cpf, String phoneNumber, LocalDate DtBirth, String password) {

        /*
            Registra a conta de um cliente ao banco.
            @return retorna um DTO com os dados principais do cliente.
         */

        try {
            Client client = new Client(name, new CPF(cpf), phoneNumber, DtBirth, new Password(password), generateNumericString(9), BigDecimal.ZERO);

            cDao.insertClient(client); // insere a entidade no banco de dados

            // retorna um DTO com os dados principais
            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new RegistrationFailedException(e.getMessage());
        }
    }

    public ClientDTO performWithdraw(String accNumber, Password password, BigDecimal value) {

        /*
            realiza um decremento de valor no saldo de uma conta associada.
            @return retorna um DTO com os dados principais do cliente.
         */

        if(accNumber == null || password == null || value == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = cDao.getClientByAccountNumber(accNumber);// busca a conta no banco de dados

            if(!Password.compare(client.getPassword().getValue(), password.getValue()))
                throw new AccessDeniedException("Não foi permitido o acesso a conta.");

            client.getAccount().withdraw(value); // Realiza uma retirada de valor da conta
            cDao.updateBalance(accNumber, client.getAccount().getBalance());  // salva a operação no banco

            // retorna um DTO com os dados principais
            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new InvalidWithdrawException(e.getMessage());
        }
    }

    public ClientDTO performDeposit(String accNumber, Password password, BigDecimal value) {

        /*
            realiza um incremento de valor no saldo de uma conta associada.
            @return retorna um DTO com os dados principais do cliente
         */

        if(accNumber == null || password == null || value == null)
            throw new InputException("Elemento invalido informado");

        try {
            Client client = cDao.getClientByAccountNumber(accNumber); // busca a conta no banco

            if(!Password.compare(client.getPassword().getValue(), password.getValue()))
                throw new AccessDeniedException("Não foi permitido o acesso a conta.");

            client.getAccount().deposit(value); // Realiza um deposito de valor da conta
            cDao.updateBalance(accNumber, client.getAccount().getBalance()); // Salva no banco

            return new ClientDTO (client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        }catch (Exception e) {
            throw new InvalidDepositException(e.getMessage());
        }

    }

    public ClientDTO transfer(String accNumber, Password password, String accountTarget, BigDecimal value) {

        /*
            Realiza uma transferencia de saldo entre a conta remetente e a conta destinataria
            @return retorna um DTO com os dados principais do cliente
         */

        if(accNumber == null || password == null || accountTarget == null || value == null)
            throw new InputException("Elemento invalido informado");


        try{
            // acessa as contas
            Client sender = cDao.getClientByAccountNumber(accNumber);
            Client receiver = cDao.getClientByAccountNumber(accountTarget);

            if(!Password.compare(sender.getPassword().getValue(), password.getValue()))
                throw new AccessDeniedException("Não foi permitido o acesso a conta.");

            sender.getAccount().withdraw(value); // Realiza uma retirada de valor da conta remetente
            receiver.getAccount().deposit(value); // Realiza uma inserção de valor na conta destinataria

            // atualiza as contas bancarias
            cDao.updateBalance(accNumber, sender.getAccount().getBalance());
            cDao.updateBalance(accountTarget, receiver.getAccount().getBalance());

            return new ClientDTO(sender.getName(), sender.getAccount().getAccountNumber(),
                    sender.getAccount().getBalance().toString());
        }catch (Exception e) {
            throw new InvalidTransferException(e.getMessage());
        }

    }

}
