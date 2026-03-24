package service;

import model.Client;
import model.Employee;
import exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeService {

    /*
    private Employee employee;
    private Bank bank;

    public EmployeeService() {
        bank = new Bank();
    }

    public boolean login(String cpf, String password) {

        // confirma se todos os dados passados são validos e se forem, se conecta a conta relacionada;

        Employee element = bank.getEmployee(cpf);

        if(cpf == null)
            throw new NullPointerException("O CPF passado não é valido!");
        if(password == null)
            throw new NullPointerException("A senha passada não é valida");
        if(element == null)
            throw new AccountNotFoundException("Não foi possivel encontrar a conta buscada");
        if(!element.getPassword().compare(password))
            return false;

        employee = element;
        return true;

    }

    public void register(String name, String cpf, String phoneNumber,
                         LocalDate DtBirth, String password, BigDecimal salary) {

        // Registra a conta de um funcionario ao banco
        employee = new Employee(name, cpf, phoneNumber, DtBirth, password, salary);

        bank.setEmployee(employee);
    }

    public void ListClients() {

        // lista todos os clientes com contas bancarias ativas
        if(employee == null)
            throw new NullPointerException("Não foi possivel estabelecer uma conexão");

        for(Client c : bank.getClients().values()) {
            System.out.println(c);
        }
    }

    public void ListEmployees() {

        // lista todos os empregados com contas ativas

        if(employee == null)
            throw new NullPointerException("Não foi possivel estabelecer uma conexão");

        for(Employee e : bank.getEmployees().values()) {
            System.out.println(e);
        }
    }
    */

}
