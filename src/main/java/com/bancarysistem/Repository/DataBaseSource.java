
package com.bancarysistem.Repository;

import com.bancarysistem.exceptions.DatabaseException;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataBaseSource {

    private String url;
    private String user;
    private String password;

    public DataBaseSource() {
        getProperties();
    }

    public Connection connect() throws SQLException {

        /// Tentará garantir um acesso valido ao banco de dados

        return DriverManager.getConnection(url, user, password);
    }


    private void getProperties() {

        try(InputStream file = getClass().getClassLoader().getResourceAsStream("properties/data.properties")) {
            Properties properties = new Properties();
            properties.load(file);

            // nome do usuario
            user = properties.getProperty("prop.server.login");
            // url do banco
            url = properties.getProperty("prop.server.host");
            // senha do banco
            password = properties.getProperty("prop.server.password");

        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Propriedades do banco não encontradas");
        }
    }
}
