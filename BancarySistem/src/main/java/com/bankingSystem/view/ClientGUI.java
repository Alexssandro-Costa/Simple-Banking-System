package com.project.bankingSystem.view;

import com.project.bankingSystem.dto.AccountDTO;
import com.project.bankingSystem.model.CPF;
import com.project.bankingSystem.model.Password;
import com.project.bankingSystem.service.ClientService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class ClientGUI {

    Scanner sc = new Scanner(System.in);
    ClientService cs = new ClientService();

    public void start() {

        boolean run = true;

        while(run){
            try {
                System.out.println("\t ---- Banco Alastor ---- ");

                System.out.println("[1] - Login");
                System.out.println("[2] - Cadastrar");
                System.out.println("[3] - EXIT");
                System.out.print(": ");
                char opt = sc.nextLine().charAt(0);

                switch (opt) {
                    case '1':
                        login();
                        break;
                    case '2':
                        register();
                        break;
                    case '3':
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


    private void login() {

        String accNumber;
       Password password;

        while(true) {
            try {
                System.out.println("\t --- Login ---");

                System.out.print("Numero da conta: ");
                accNumber = sc.nextLine();

                System.out.print("Senha: ");
                password = new Password(sc.nextLine());

                operations(cs.login(accNumber, password));
                break;

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Deseja tentar novamente? [N/S]: ");
                char opt = sc.nextLine().toUpperCase().charAt(0);
                if(opt == 'N')
                    break;
            }

        }

    }

    private void register() {

        String name;
        CPF cpf;
        String number;
        LocalDate date;
        Password password;

        while(true) {
            try {

                System.out.println("\t --- Registro --- ");
                System.out.print("Nome: ");
                name = sc.nextLine();

                System.out.print("CPF: ");
                cpf = new CPF(sc.nextLine());

                System.out.print("Numero de telefone: ");
                number = sc.nextLine();

                System.out.print("Data de nascimento(xxxx-xx-xx): " );
                date = LocalDate.parse(sc.nextLine());

                System.out.print("Senha: ");
                password = new Password(sc.nextLine());

                operations(cs.register(name, cpf, number, date, password));
                break;


            }catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Deseja tentar novamente? [S/N]: ");
                char opt = sc.nextLine().toUpperCase().charAt(0);
                if(opt == 'N')
                    break;

            }

        }

    }


    private void operations(AccountDTO c) {

        AccountDTO client = c;
        boolean run = true;
        char opt;
        BigDecimal value;
        Password password;
        CPF cpf;

        while(run) {

            try {

                System.out.printf(client.toString());

                System.out.printf("[1] - Saque %n[2] - Deposit %n[3] - Transferência %n" +
                        "[4] - Excluir conta %n[5] -  Sair da conta %n");
                System.out.print(": ");
                opt = sc.nextLine().charAt(0);

                switch (opt) {
                    case '1':
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        System.out.print("Senha de confirmação: ");
                        password = new Password(sc.nextLine());
                        client = cs.withdraw(c.accountNumber(), password, value); // atualiza o valor
                        break;
                    case '2':
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        System.out.print("Senha de confirmação: ");
                        password = new Password(sc.nextLine());
                        client = cs.deposit(c.accountNumber(), password, value);
                        break;
                    case '3':
                        System.out.print("Numero da conta para transferencia: ");
                        String accTarget = sc.nextLine();
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        System.out.print("Senha de confirmação: ");
                        password = new Password(sc.nextLine());
                        client = cs.transfer(c.accountNumber(),  accTarget, password, value); // atualiza o valor
                        break;

                    case '4':
                        System.out.print("tem certeza que deseja deletar sua conta? [S/N]: ");
                        opt = sc.nextLine().toUpperCase().charAt(0);
                        if(opt != 'S')
                            break;

                        System.out.print("Digite seu CPF: ");
                        cpf = new CPF(sc.nextLine());

                        System.out.print("Senha de confirmação: ");
                        password = new Password(sc.nextLine());

                        cs.deleteAccount(c.accountNumber(), cpf, password);

                    case '5':
                        System.out.println("Saindo...");
                        run = false;
                        break;
                    default:
                        System.out.println("Opção invalida");
                }

            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
