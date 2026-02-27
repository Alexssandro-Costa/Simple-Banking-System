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
    private final Path path;

    public BankAccountFileRepository(String link) {
        path = Paths.get(link);
    }
    public BankAccountFileRepository() {
        this("data\\accountsPersistence.CSV");

    }


    public void save(ArrayList<String> arr) throws Exception {
        /*
            Salva todos os elementos passados em um arquivo de persistência;
         */

        if (arr == null)
            throw new NullPointerException("A estrutura passada é nula");

        if (Files.notExists(path))
            Files.createFile(path); // cria arquivo caso não exista

        Files.writeString(path, "", StandardOpenOption.TRUNCATE_EXISTING); // limpa o arquivo

        // salva todas os elementos no arquivo
        for (String s : arr) {
            Files.writeString(path, s, StandardOpenOption.APPEND);
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

            // salva cada linha no array
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
