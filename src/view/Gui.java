package view;

import model.Account;
import model.Bank;
import model.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Gui {

    public static void start() {

        boolean run = true;
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);

        while(run) {
            try {

                System.out.println("|---------------------------------------|");
                System.out.println("\t\t ----- BANCO ALASTOR -----");

                System.out.println("\tOpções: ");
                System.out.println("\t[1] - Cadastrar ");
                System.out.println("\t[2] - Remover ");
                System.out.println("\t[3] - Acessar ");
                System.out.println("\t[4] - Listar todos");
                System.out.println("\t[5] - Sair");

                System.out.print("\t: ");
                String opt = sc.nextLine();
                System.out.println("|---------------------------------------|");

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
                        System.out.println("\tSaindo do programa...");
                        run = false;
                        break;
                    default:
                        System.out.println("\tOpção invalida");
                        break;
                }
            }catch (Exception e) {
                System.err.println("\t" + e.getMessage());
            }
        }
    }

    private static void register(Scanner sc, Bank bank) throws Exception {

        /*
        Registra uma conta valida.
        @param sc: lê os dados
        @param bank: objeto onde as contas serão salvas
         */

        String name = null;
        String cpf = null;
        BigDecimal deposit = BigDecimal.valueOf(0.0);


        System.out.println("\t\t--- CADASTRO ---");

        System.out.print("\tNome do cliente: ");
        name = sc.nextLine();

        System.out.print("\tCPF do cliente: ");
        cpf = sc.nextLine();

        System.out.print("\tDeposito inicial: ");
        deposit = BigDecimal.valueOf(sc.nextDouble());
        sc.nextLine();

        bank.registerAccount( new Person(name, cpf), deposit);
        System.out.println("Conta registrada com sucesso");

    }

    private static void remove(Scanner sc, Bank bank) throws Exception {

        /*
        Remove uma conta valida.
        @param sc: lê os dados
        @param bank: objeto onde as contas serão buscadas
         */

        String accountNumber = null;
        Account account = null;

        System.out.println("\t\t--- DELEÇÃO ---");
        System.out.print("\tNumero de conta: ");
        accountNumber = sc.nextLine();

        account = bank.removeAccount(accountNumber);

        System.out.println("Conta de titular: " + account.getHolder().getName() + " Removida com sucesso");
    }


    private static void accessAccount(Scanner sc, Bank bank) throws Exception {

        /*
        Acessa uma conta valida.
        @param sc: lê os dados
        @param bank: objeto onde as contas serão buscadas
         */

        System.out.println("\t\t--- CONTA BANCARIA ---");

        String accountNumber = null;
        Account account = null;
        String opt = null;
        double value = 0.0;
        boolean run = true;

        System.out.print("\tNumero da conta: ");
        accountNumber = sc.nextLine();

        account = bank.getAccount(accountNumber);
        if (account == null)
            throw new Exception("Não foi possivel encontrar a conta buscada");

        while (run) {

            try {

                System.out.println("\t" + account);
                System.out.println("\t[1] - Saque");
                System.out.println("\t[2] - Deposito");
                System.out.println("\t[3] - Deslogar ");

                System.out.print("\t: ");
                opt = sc.nextLine();

                System.out.println();
                switch (opt) {
                    case "1":
                        System.out.print("\t Valor de saque: ");
                        value = sc.nextDouble();
                        sc.nextLine();
                        account.withdraw(BigDecimal.valueOf(value));
                        break;
                    case "2":
                        System.out.println("\t Valor de deposito: ");
                        value = sc.nextDouble();
                        account.deposit(BigDecimal.valueOf(value));
                        break;
                    case "3":
                        System.out.println("\t Saindo...");
                        run = false;
                        break;
                    default:
                        System.out.println("\t Opção invalida");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void listAll(Bank bank) throws Exception {

        /*
        Mostra todas as contas cadastradas na tela.
         */

        ArrayList<Account> accounts = bank.getAccounts();

        if(accounts.isEmpty())
            throw new Exception("não há nenhuma conta cadrastrada");

        System.out.println("\t\t--- LISTAGEM DE CONTAS ---");

        for(Account a : accounts) {

            if(a != null) {
                System.out.println("\t------------");
                System.out.println(a);
            }
        }

    }





}
