package view;

import model.Client;
import service.ClientService;

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

        String cpf;
        String password;

        while(true) {
            try {
                System.out.println("\t --- Login ---");

                System.out.print("CPF: ");
                cpf = sc.nextLine();

                System.out.print("Senha: ");
                password = sc.nextLine();

                operations(cs.login(cpf, password));
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
        String cpf;
        String number;
        LocalDate date;
        String password;

        while(true) {
            try {

                System.out.println("\t --- Registro --- ");
                System.out.print("Nome: ");
                name = sc.nextLine();

                System.out.print("CPF: ");
                cpf = sc.nextLine();

                System.out.print("Numero de telefone: ");
                number = sc.nextLine();

                System.out.print("Data de nascimento(xxxx-xx-xx): " );
                date = LocalDate.parse(sc.nextLine());

                System.out.print("Senha: ");
                password = sc.nextLine();

                operations(cs.Register(name, cpf, number, date, password));
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


    private void operations(Client client) {

        boolean run = true;
        char opt;
        BigDecimal value;

        while(run) {

            try {

                System.out.printf("Titular: %s %nNumero de conta: %s %nBalanço: R$:%s %n",
                        client.getName(), client.getAccount().getAccountNumber(), client.getAccount().getBalance().toString());

                System.out.printf("[1] - Saque %n[2] - Deposit %n[3] - Transferência %n[4] - Sair da conta %n");
                System.out.print(": ");
                opt = sc.nextLine().charAt(0);

                switch (opt) {
                    case '1':
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        cs.performWithdraw(value);
                        break;
                    case '2':
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        cs.performDeposit(value);
                        break;
                    case '3':
                        System.out.print("Numero da conta para transferencia: ");
                        String acc = sc.nextLine();
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        cs.transfer(acc, value);
                        break;
                    case '4':
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
