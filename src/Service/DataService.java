package Service;

import model.Account;
import persistence.BankAccountFileRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class DataService {

    // classe intermediaria. terá como função, receber os dados brutos do repositorio,
    // trata-los e envia-los pra classe banco. E receber os dados do banco,
    // transforma-los em dados brutos e envia-los pro repositorio

    private BankAccountFileRepository repo = new BankAccountFileRepository();

    protected boolean set(HashMap<String, Account> accounts) throws Exception {

        /*
        Cria um ArrayList de Strings apartir de um HashMap e o entrega ao repositorio.
         */

        ArrayList<String> arr = new ArrayList<>();

        for(Account acc : accounts.values()) {
            if(acc != null)
                arr.add(acc.toString());
        }
        repo.save(arr);  // envia ao repositorio

        return true;

    }

    protected HashMap<String, Account> get() throws Exception {

        /*
        Trata os dados brutos recebidos do repositorio e os transforma em uma conta valida.
         */

        HashMap<String, Account> accounts = new HashMap<>();
        String[] attributes = new String[4];
        String accountNumber, name, cpf;
        BigDecimal value;
        ArrayList<String> elements = repo.load();

        for(String s : elements) {

            if(s != null) {
                attributes = s.split(";");
                accountNumber = attributes[0];
                name = attributes[1];
                cpf = attributes[2];
                value = BigDecimal.valueOf( Double.parseDouble(attributes[3]));
                accounts.put(accountNumber, new Account(accountNumber, name, cpf, value));
            }
        }
        return accounts;
    }


}
