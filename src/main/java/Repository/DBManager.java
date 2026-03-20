package Repository;

import exceptions.InputException;

import java.sql.*;

public class DBManager {

    private String url;
    private String user;
    private String password;

    public DBManager(String url, String user, String password) {

        try {
            this.url = url;
            this.user = user;
            this.password = password;
        }catch (Exception e) {
            throw new InputException("Especificações do DATABASE invalidas.");
        }
    }

    public DBManager() {
        this("jdbc:postgresql://localhost:5432/Bank", "postgres", "1708");
    }

    public Connection connect() throws SQLException {

        /// Tentará garantir um acesso valido ao banco de dados

        return DriverManager.getConnection(url, user, password);
    }




}
