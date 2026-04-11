
package com.project.bankingSystem.repository;

import com.project.bankingSystem.exceptions.DatabaseException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


/**
 * Classe que garante a conexão o banco de dados.
 * @author Alexssandro
 * @since release 1
 * @version 2.0
 */
public class DataBaseSource {

    private String url;
    private String user;
    private String password;

    public DataBaseSource() {
        getProperties();
    }

    /**
     * Cria uma conexão com o banco de dados especificado.
     * @return Connection Objeto Connection que mantêm conexão com o banco de dados.
     * @throws SQLException Lançada quando não é possivel garantir uma conexão com o banco de dados.
     */
    public Connection connect() throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }


    /**
     * Recupera as propriedades do banco de dados do arquivo data.properties.
     * @exception DatabaseException Lançada quando não for possivel abir ou recuperar os dados do arquivo.
     */
    private void getProperties() {

        // acessa o arquivo
        try(InputStream file = getClass().getClassLoader().getResourceAsStream("properties/data.properties")) {
            Properties properties = new Properties();
            properties.load(file);

            // recupera o nome do usuario
            user = properties.getProperty("prop.server.login");
            // Recupera o url do banco
            url = properties.getProperty("prop.server.host");
            // Recupera a senha do banco
            password = properties.getProperty("prop.server.password");

        } catch (Exception e) {
            e.getStackTrace();
            throw new DatabaseException("Propriedades do banco não encontradas");
        }
    }
}
