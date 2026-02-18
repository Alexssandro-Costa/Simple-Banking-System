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

        if(Files.notExists(path))
            throw new FileNotFoundException("Não foi possivel localizar o arquivo");
        if(accounts == null)
            throw new NullPointerException("Não foi possivel salvar as contas");

        // arquivo temporario
        Path tempFile = Files.createTempFile("arquivo","Temporario");
        Files.copy(path, tempFile); // cria um arquivo temporario contendo as contas slvas
        Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING).close(); // limpa o arquivo padrão

        // salva todas as contas no arquivo

        try {
            for (Account account : accounts.values()) {
                Files.writeString(path, String.format("%s;%s;%s;%s%n", account.getAccountNumber(),
                        account.getHolder().getName(), account.getHolder().getCPF(), account.getBalance()));
            }
        }catch (Exception e) {
            Files.copy(tempFile, path); // retorna o arquivo ao ultimo salvamento
            throw new Exception("Não foi possivel salvar em arquivo");
        }
    }



}
