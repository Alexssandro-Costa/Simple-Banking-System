package persistence;

import model.Account;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class BankAccountFileRepository {

    // arquivo de persistência padrão
    private final Path path = Paths.get("C:\\Users\\user\\Downloads\\Java\\Projetos\\data\\accountsPersistence.CSV");

    public void save(HashMap<String, Account> accounts) throws Exception {
        /*
            Salva todos os elementos passados em um arquivo de persistência;
         */

        if (Files.notExists(path))
            Files.createFile(path);
        if (accounts == null)
            throw new NullPointerException("Não foi possivel salvar as contas");

        Files.writeString(path, "", StandardOpenOption.TRUNCATE_EXISTING); // limpa o arquivo
        // salva todas os elementos no arquivo
        for (Account account : accounts.values()) {
            Files.writeString(path, account.toString(), StandardOpenOption.APPEND);
        }
    }

    public ArrayList<String> load() throws Exception {
        /*
            Lê todos os elementos de um arquivo e os guarda em um Array que será retornado.
         */

        if (Files.notExists(path))
            throw new Exception("Não foi possivel encontrar o arquivo:");

        ArrayList<String> elements = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(path)) {

            String line = reader.readLine();
            while(line != null) {
                elements.add(line);
                line = reader.readLine();
            }

            return elements;
        } catch (Exception e) {
            throw new Exception("Erro ao Ler o arquivo: " + e.getMessage());
        }

    }

}
