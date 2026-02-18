package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<Account>();
    }

    public void registerAccount(Person holder, BigDecimal initialDeposit) {

        /*
        Cadrastra uma nova conta no banco.

        @param holder: titular da conta
        @param inititaldeposit: deposito inicial;
         */

        if(holder == null)
            throw  new NullPointerException("O titular da conta passado é nulo");
        if(initialDeposit == null)
            throw new NullPointerException("Deposito initial nulo");

        Account newAccount = new Account(holder, initialDeposit, generateAccountNumber());

        accounts.add(newAccount);
    }

    public Account removeAccount(String accountNumber) throws Exception {

        /*
        Remove a conta bancaria que pertença ao numero de conta passado.

        @param accountNumber: numero da conta
        @return retorna a conta bancaria removida do banco.
         */

        Account subject = getAccount(accountNumber);
        if(subject == null)
            throw new Exception("Não foi possivel encontrar a conta buscada");

        accounts.remove(subject);

        return subject;

    }


    public Account getAccount(String accountNumber) {

        /*

       Busca e retorna a primeira conta com o numero de conta corresponde.

        @param accountNumber: Numero da conta
        @return retorna a conta encontrada ou um elemento nulo
         */

        if(accountNumber == null)
            throw new NullPointerException("O numero da conta passado para busca é nulo");


        for(Account a : accounts ) {

            if(a != null) {
                if(a.getAccountNumber().equals(accountNumber))
                    return a;
            }
        }

        return null;
    }


    public ArrayList<Account> getAccounts() throws Exception {

        if(accounts.isEmpty())
            throw new Exception("A lista de contas está vazia");

        return accounts;
    }


    private String generateAccountNumber() {

        /*
        cria um numero de conta aleatorio e o retorna.
        @return retorna um numero de conta valido é unico.
         */

        Random r = new Random();
        StringBuilder number = new StringBuilder();

        for(int i = 0; i < 9; i++) {

            number.append(String.valueOf(r.nextInt(1, 9)));
        }

        if(getAccount(String.valueOf(number)) == null)
            return String.valueOf(number);

        return generateAccountNumber();

    }

}
