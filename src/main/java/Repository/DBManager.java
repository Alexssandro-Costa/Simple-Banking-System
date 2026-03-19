package Repository;

import exceptions.DatabaseException;
import exceptions.InputException;
import model.Account;
import model.Client;

import java.sql.*;

public class DBManager {

    private String url;
    private String user;
    private String password;
    private Connection conn;

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

    private void connect() {

        /// Tentará garantir um acesso valido ao banco de dados

        try {
            conn = DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            throw new DatabaseException("Não foi possivel acessar ao DATABASE");
        }
    }

    public Client search(String cpf, String password) throws SQLException {

        /// Busca e retorna os dados da conta relacionado ao id informado

        connect();
        String sql = "SELECT * FROM CLIENTE c join CONTA ct on c.cpf = ct.idcliente WHERE c.cpf = ? and c.senha = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new Client(rs.getString("nome"), rs.getString("cpf"), rs.getString("telefone"),
                        rs.getDate("datanascimento").toLocalDate(), rs.getString("senha"),
                        new Account(rs.getString("numeroconta"), rs.getBigDecimal("balanco")));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        catch (Exception e) {
            throw new DatabaseException("Não foi possivel acessar a conta buscada", e);
        }
        finally {
            conn.close();
        }

        return null;
    }

    public void insert(Client c) throws SQLException {

        ///  insere as informações de um novo cliente no banco de dados
        connect();
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("insert into cliente(nome, cpf, telefone, datanascimento, senha) values (?, ?, ?, ?, ?)");
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getCPF());
            stmt.setString(3, c.getPhone().getNumber());
            stmt.setDate(4,Date.valueOf(c.getDTBirth()));
            stmt.setString(5, c.getPassword().getValue());
            stmt.executeUpdate();

            stmt = conn.prepareStatement("insert into conta(idcliente, numeroconta, balanco) values (?,?, ?)");
            stmt.setString(1, c.getCPF());
            stmt.setString(2, c.getAccount().getAccountNumber());
            stmt.setBigDecimal(3, c.getAccount().getBalance());
            stmt.executeUpdate();

            conn.commit();
        }catch (Exception e){
            conn.rollback();
            throw new DatabaseException("Não foi possivel salvar os dados no banco de dados");
        }
        finally {
            conn.close();
            stmt.close();
        }
    }



}
