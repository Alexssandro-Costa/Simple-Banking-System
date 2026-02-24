package persistence;

import model.Account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;

public class BankAccountFileRepository {

    // arquivo de persistência padrão
    private final Path path = Paths.get("C:\\Users\\user\\Downloads\\Java\\Projetos\\data\\accountsPersistence.CSV");

    public void save(HashMap<String, Account> accounts) throws Exception {

        if (Files.notExists(path))
            Files.createFile(path);
        if (accounts == null)
            throw new NullPointerException("Não foi possivel salvar as contas");

        Files.writeString(path, "", StandardOpenOption.TRUNCATE_EXISTING); // limpa o arquivo
        // salva todas as contas no arquivo
        for (Account account : accounts.values()) {
            Files.writeString(path, account.toString(), StandardOpenOption.APPEND);
        }
    }

    public HashMap<String, Account> load()
}
