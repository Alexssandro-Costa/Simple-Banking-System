package service;

import DTO.ClientDTO;
import exceptions.*;
import model.Account;
import model.Client;
import utility.Generator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientService {

    private Client client;
    private DataService dt;

    public ClientService() {
        dt = new DataService();
    }

    public ClientDTO login(String cpf, String password) {

        /// confirma se todos os dados passados são validos e se forem, se conecta a conta relacionada;


        validateCPF(cpf);
        validatePassword(password);

        try {
            client = dt.select(cpf, password);
            if (client == null)
                throw new NullPointerException("Não foi possivel encontrar a conta buscada");

            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new AccountNotFoundException(e.getMessage());
        }

    }

    public ClientDTO Register(String name, String cpf, String phoneNumber, LocalDate DtBirth, String password) {


        // Registra a conta de um cliente ao banco

        client = new Client(name, cpf, phoneNumber, DtBirth, password,
                new Account(Generator.generateNumericString(9), BigDecimal.ZERO));

        try {
            dt.insert(client);
            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(),
                    client.getAccount().getBalance().toString());

        } catch (Exception e) {
            throw new AccountNotFoundException(e.getMessage());
        }
    }

    public ClientDTO performWithdraw(BigDecimal value) {

        // realiza uma retirada de valor de uma conta associada

        if (client == null)
            throw new AccountNotFoundException("Não foi possivel estabelecer uma conexão");

        try {
            client.getAccount().withdraw(value);
            dt.update(client.getAccount().getAccountNumber(),value.negate());
            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(), client.getAccount().getBalance().toString());

        }catch (Exception e) {
            throw new InvalidWithdrawException(e.getMessage());
        }
    }

    public ClientDTO performDeposit(BigDecimal value) {

        /// realiza um incremento de valor de uma conta associada

        if(client == null)
            throw new AccountNotFoundException("Não foi possivel estabelecer uma conexão");

        try {
            client.getAccount().deposit(value);
            dt.update(client.getAccount().getAccountNumber(), value);
            return new ClientDTO (client.getName(), client.getAccount().getAccountNumber(), client.getAccount().getBalance().toString());
        }catch (Exception e) {
            throw new InvalidDepositException(e.getMessage());
        }

    }

    public ClientDTO transfer(String accountTarget, BigDecimal value) {

        /// Realiza uma transferencia de de saldo entre a conta conectada e a conta destinataria

        if(client == null)
            throw new AccountNotFoundException("Não foi possivel estabelecer uma conexão");

        if(accountTarget == null)
            throw new InputException("Numero da conta invalido");
        if(value == null || value.compareTo(BigDecimal.ZERO) < 0)
            throw new InputException("Valor de transferência invalido");
        if(accountTarget.length() != 9)
            throw new IllegalArgumentException("ERRO! O numero da conta deve conter 9 caracteres");

        try{

            client.getAccount().withdraw(value);
            dt.update(client.getAccount().getAccountNumber(), accountTarget, value);
            return new ClientDTO(client.getName(), client.getAccount().getAccountNumber(), client.getAccount().getBalance().toString());
        }catch (Exception e) {
            throw new InvalidTransferException(e.getMessage());
        }

    }


    private void validateCPF(String input) {

       ///  Verifica se o CPF passado está em um formato valido para consulta

        if(input == null)
            throw new NullPointerException("O CPF passado não é valido!");

        Pattern pattern = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
        Matcher matcher = pattern.matcher(input);

        if(!matcher.matches())
            throw new InvalidFormatException("O CPF passado está em um formato não valido.");
    }

    private void validatePassword(String input) {

        ///  Verifica se a senha passada está em formato permitido pelo Banco

        if(input == null)
            throw new NullPointerException("A senha passada é invalida");

        if(input.length() < 4)
            throw new InvalidPasswordException("A senha deve conter ao menos 4 caracteres");

        /// determina que a senha deve conter ao menos uma letra e um numero e, ter um minimo de 4 caracteres
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{4,}$");
        Matcher matcher = pattern.matcher(input);

        if(!matcher.matches())
            throw new InvalidPasswordException("Invalida! A senha deve conter ao menos uma letra e um numero.");

    }


}
