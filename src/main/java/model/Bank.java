package model;

import java.util.HashMap;

public class Bank {

    private HashMap<String, Client> clients;
    private HashMap<String, Employee> employees;
    private HashMap<String, Account> accounts;


    public Bank(HashMap<String, Client> cll, HashMap<String, Employee> emp, HashMap<String, Account> acc) {
        this.clients = cll;
        this.employees = emp;
        this.accounts = acc;
    }
    public Bank() {
        this(new HashMap<String, Client>(), new HashMap<String, Employee>(), new HashMap<String, Account>());
    }


    public HashMap<String, Client> getClients() {
        return clients;
    }

    public HashMap<String, Employee> getEmployees() {
        return employees;
    }

    public Client getClient(String cpf) {

        if(cpf == null)
            throw new NullPointerException("Insira um cpf valido para pesquisa");

        return clients.get(cpf);
    }

    public void setClient(Client client) {


        if(client == null)
            throw new NullPointerException("O elemento cliente passado é invalido");

        clients.put(client.getCPF(), client);
        accounts.put(client.getAccount().getAccountNumber(), client.getAccount());
    }

    public Employee getEmployee(String cpf) {

        if(cpf == null)
            throw new NullPointerException("Insira um id valida para pesquisa");

        return employees.get(cpf);
    }

    public void setEmployee(Employee employee) {

        if(employee == null)
            throw new NullPointerException("O elemento empregado passado é invalido");

        employees.put(employee.getCPF(), employee);
    }

    public Account getAccount(String accountNumber) {

        if(accountNumber == null)
            throw new NullPointerException("O numero de conta passado é invalido");

        return accounts.get(accountNumber);
    }
}
