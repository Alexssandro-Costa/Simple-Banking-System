package view;

import model.Account;
import model.Bank;
import persistence.BankAccountFileRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class Gui {

    public static void start() throws Exception {

        boolean run = true;
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);

        while(run) {
            System.out.println("\t\t ----- BANCO ALASTOR -----");

            System.out.println("Opções: ");
            System.out.println("[1] - Cadastrar ");
            System.out.println("[2] - Remover ");
            System.out.println("[3] - Acessar ");
            System.out.println("[4] - Listar todos");
            System.out.println("[5] - Sair");

            System.out.print(": ");
            String opt = sc.nextLine();

            try{
                switch (opt) {
                    case "1":
                        register(sc, bank);
                        break;
                    case "2":
                        remove(sc, bank);
                        break;
                    case "3":
                        accessAccount(sc, bank);
                        break;
                    case "4":
                        listAll(bank);
                        break;
                    case "5":
                        System.out.println("saindo...");
                        run = false;
                        break;
                    default:
                        System.out.println("Opção invalida");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            finally {
                bank.update();
            }
        }
    }

    private static void register(Scanner sc, Bank bank) {

        /*
        Registra uma conta valida.
        @param sc: lê os dados
        @param bank: objeto onde as contas serão salvas
         */

        String name;
        String cpf;
        boolean run = true;
        BigDecimal deposit;

        while(run) {
            try {
                System.out.println("\t--- CADASTRO ---");

                System.out.print("Nome do cliente: ");
                name = sc.nextLine();

                System.out.print("CPF do cliente: ");
                cpf = sc.nextLine();

                System.out.print("Deposito inicial: ");
                deposit = BigDecimal.valueOf(sc.nextDouble());
                sc.nextLine();

                bank.registerAccount(name, cpf, deposit);
                System.out.println("Conta registrada com sucesso");
                run = false;
            } catch (Exception e) {
               System.out.println(e.getMessage());
            }
        }

    }

    private static void remove(Scanner sc, Bank bank) throws Exception {

        /*
        Remove uma conta valida.
        @param sc: lê os dados
        @param bank: objeto onde as contas serão buscadas
         */

        String accountNumber;
        Account account;


        System.out.println("\t--- DELEÇÃO ---");
        System.out.print("Numero de conta: ");
        accountNumber = sc.nextLine();

        account = bank.removeAccount(accountNumber);
        if (account == null)
            throw new NullPointerException("Não foi possivel encontrar a conta buscada");

        System.out.println("Conta do titular: " + account.getName() + "." + " Removida com sucesso");
    }


    private static void accessAccount(Scanner sc, Bank bank) {

        /*
        Acessa uma conta valida e realiza funções de retirada ou deposito de dinheiro.
        @param sc: lê os dados
        @param bank: objeto onde as contas serão buscadas
         */

        System.out.println("\t--- CONTA BANCARIA ---");

        String accountNumber;
        Account account;
        String opt;
        double value;
        boolean run = true;

        System.out.print("Numero da conta: ");
        accountNumber = sc.nextLine();


        account = bank.getAccount(accountNumber);
        if (account == null)
            throw new NullPointerException("Não foi possivel encontrar a conta buscada");

        while (run) {

            System.out.printf("Titular: " + account.getName() + "%nCPF: " + account.getCPF() + "%nBalanço: R$:" + account.getBalance() + "%n");
            System.out.println("[1] - Saque");
            System.out.println("[2] - Deposito");
            System.out.println("[3] - Deslogar ");

            System.out.print(": ");
            opt = sc.nextLine();

            try {
                System.out.println();
                switch (opt) {
                    case "1":
                        System.out.print("Valor de saque: R$:");
                        value = sc.nextDouble();
                        sc.nextLine();
                        account.withdraw(BigDecimal.valueOf(value));
                        break;
                    case "2":
                        System.out.println("Valor de deposito: R$:");
                        value = sc.nextDouble();
                        sc.nextLine();
                        account.deposit(BigDecimal.valueOf(value));
                        break;
                    case "3":
                        System.out.println("Saindo...");
                        run = false;
                        break;
                    default:
                        System.out.println("Opção invalida");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void listAll(Bank bank) {

        /*
        Mostra todas as contas cadastradas na tela.
         */

        HashMap<String, Account> accounts = bank.getAccounts();

        if (accounts == null)
            throw new NullPointerException("não há nenhuma conta cadastrada");

        System.out.println("\t--- LISTAGEM DE CONTAS ---");

        for (Account a : accounts.values()) {
            if (a != null)
                System.out.println(a);
            }
    }

}
