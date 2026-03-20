package view;

import DTO.ClientDTO;
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


    private void operations(ClientDTO c) {

        ClientDTO client = c;
        boolean run = true;
        char opt;
        BigDecimal value;

        while(run) {

            try {

                System.out.printf(client.toString());

                System.out.printf("[1] - Saque %n[2] - Deposit %n[3] - Transferência %n[4] - Sair da conta %n");
                System.out.print(": ");
                opt = sc.nextLine().charAt(0);

                switch (opt) {
                    case '1':
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        client = cs.performWithdraw(value); // atualiza o valor
                        break;
                    case '2':
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        client = cs.performDeposit(value);
                        break;
                    case '3':
                        System.out.print("Numero da conta para transferencia: ");
                        String accNum = sc.nextLine();
                        System.out.print("Valor R$: ");
                        value = sc.nextBigDecimal();
                        sc.nextLine();
                        client = cs.transfer(accNum, value); // atualiza o valor
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
