package model;

import Service.DataService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

public class Bank {

    private HashMap<String, Account> accounts;
    private DataService service;

    public Bank() throws Exception {
        service = new DataService();
        accounts = service.get();
    }

    public void registerAccount(String name, String cpf, BigDecimal initialDeposit) throws Exception {

        /*
        Cadrastra uma nova conta no banco.

        @param inititaldeposit deposito inicial
         */

        Account newAccount = new Account(generateAccountNumber(), name, cpf, initialDeposit);
        accounts.put(newAccount.getAccountNumber(), newAccount);
    }

    public Account removeAccount(String accountNumber) {

        /*
        Remove a conta bancaria que pertença ao numero de conta passado.

        @param accountNumber: numero da conta
        @return retorna a conta bancaria removida do banco.
         */

        if(accountNumber == null)
            throw new NullPointerException("O numero da conta passado para busca é nulo");

        return accounts.remove(accountNumber);

    }


    public Account getAccount(String accountNumber) {

        /*

       Busca e retorna a primeira conta com o numero de conta corresponde.

        @param accountNumber: Numero da conta
        @return retorna a conta encontrada ou um elemento nulo
         */

        if(accountNumber == null)
            throw new NullPointerException("O numero da conta passado para busca é nulo");

        return accounts.get(accountNumber);
    }


    public HashMap<String, Account> getAccounts() {

        if(accounts.isEmpty())
          return null;

        return accounts;
    }


    private String generateAccountNumber() {

        /*
        cria um numero de conta aleatorio valido.
        @return retorna um numero de conta valido é unico.
         */

        Random r = new Random();
        StringBuilder number = new StringBuilder();

        for(int i = 0; i < 9; i++) {

            number.append((r.nextInt(1, 9)));
        }

        if(getAccount(String.valueOf(number)) == null)
            return String.valueOf(number);

        return generateAccountNumber();

    }

    public void update() throws Exception {
        service.set(accounts);
    }

}
