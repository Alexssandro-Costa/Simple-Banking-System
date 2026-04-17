package com.project.bankingSystem.dao;

import com.project.bankingSystem.repository.DataBaseSource;
import com.project.bankingSystem.exceptions.AccountNotFoundException;
import com.project.bankingSystem.exceptions.DatabaseException;
import com.project.bankingSystem.model.CPF;
import com.project.bankingSystem.model.Client;
import com.project.bankingSystem.model.Password;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Classe que implementa metodos de Inserção, atualização, Busca e exclusão de dados
 * @author Alexssandro
 * @since release 1
 * @version 2.0
 */

public class ClientDAO implements IClientDAO {

    private static DataBaseSource db;

    public ClientDAO() {
        db = new DataBaseSource();
    }

    /**
     * Busca dados essênciais de um cliente no banco de dados.
     * @param accNumber Numero da conta bancaria.
     * @return Client Tipo cliente com dados sobre o nome, senha, balanço e numero da conta de um cliente.
     * @exception AccountNotFoundException Lançada quando não é encontrada uma conta.
     * @exception DatabaseException Lançada quando ocorre um erro no banco de dados.
     */
    @Override
    public Client getClientByAccountNumber(String accNumber) {

        String sql = "select c.nome, c.senha, ct.balanco " +
                "from cliente c " +
                "join conta ct on c.id = ct.cliente_id " +
                "where ct.numeroconta = ?";

        try(Connection conn = db.connect(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, accNumber); // passa o numero da conta pro comando sql
            ResultSet rs = stm.executeQuery(); // dados recuperados do banco

            if (!rs.next())
                throw new AccountNotFoundException("Não foi possivel encontrar a conta especificada.");

            return new
                    Client(rs.getString("nome"),
                    accNumber,
                    rs.getBigDecimal("balanco"),
                    new Password(rs.getString("senha")));

        } catch (SQLException e) {
            throw new DatabaseException("Não foi possivel conectar ao banco.");
        } catch (Exception e) {
            throw new AccountNotFoundException("Não foi possivel encontrar a conta especificada.");
        }

    }

    /**
     * Busca Todas as informações de um cliente no banco de dados.
     * @param cpf Numero do CPF.
     * @return Client objeto Client contêndo todos os dados de um cliente.
     * @exception AccountNotFoundException Lançada quando não é encontrada uma conta.
     * @exception DatabaseException Lançada quando ocorre um erro no banco de dados.
     */
    @Override
    public Client getClientInformation(CPF cpf) {

        String sql = "select c.nome, c.cpf, c.telefone, c.datanascimento, c.senha, " +
                "ct.balanco, ct.numeroconta " +
                "from cliente c " +
                "join conta ct on c.id = ct.cliente_id " +
                "where c.cpf = ?";

        try(Connection conn = db.connect(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, cpf.getValue()); // passa o cpf para comando sql
            ResultSet rs = stm.executeQuery(); // dados recuperados do banco

            if (!rs.next())
                throw new AccountNotFoundException("Não foi possivel encontrar a conta especificada.");

            return new
                    Client(rs.getString("nome"),
                    new CPF(rs.getString("cpf")),
                    rs.getString("telefone"),
                    rs.getDate("datanascimento").toLocalDate(),
                    new Password(rs.getString("senha")),
                    rs.getString("numeroconta"),
                    rs.getBigDecimal("balanco"));

        }catch (SQLException e) {
            throw new DatabaseException("Não foi possivel conectar ao banco.");
        } catch (Exception e) {
            throw new AccountNotFoundException("Não foi possivel encontrar a conta especificada.");
        }

    }

    /**
     * Insere um novo cliente no banco de dados.
     * @param client Objeto cliente contêndo toda a informação necessaria
     * @exception DatabaseException Lançada quando ocorre um erro no banco de dados.
     */
    @Override
    public void insertClient(Client client) {


        String clientSql = "insert into cliente(nome, cpf, telefone, datanascimento, senha) values (?, ?, ?, ?, ?)";
        String accountSql = "insert into conta(cliente_id, numeroconta, balanco) values (?,?, ?)";

        try(Connection conn = db.connect()) {
            conn.setAutoCommit(false);

            // insere os dados na tabela cliente
            try(PreparedStatement stm = conn.prepareStatement(clientSql, Statement.RETURN_GENERATED_KEYS)) {

                stm.setString(1, client.getName());
                stm.setString(2, client.getCpf().getValue());
                stm.setString(3, client.getPhone().getNumber());
                stm.setDate(4, Date.valueOf(client.getDTBirth()));
                stm.setString(5, client.getPassword().getValue());
                stm.executeUpdate();

                // recupera o id gerado como chave para a tabela cliente
                try(ResultSet rs = stm.getGeneratedKeys()) {
                    rs.next();
                    int id = rs.getInt(1);

                    // insere os dados na tabela conta
                    try(PreparedStatement stm2 = conn.prepareStatement(accountSql)) {

                        stm2.setInt(1, id);
                        stm2.setString(2, client.getAccount().getAccountNumber());
                        stm2.setBigDecimal(3, client.getAccount().getBalance());
                        stm2.executeUpdate();
                    }
                }

            }catch (Exception e) {
                conn.rollback(); // retorna o banco pro ultimo commit
                throw new DatabaseException("Não foi possivel salvar os dados da conta");
            }

            conn.commit(); // salva o estado do banco
        }catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Não foi possivel conectar ao banco.");
        }
    }

    /**
     * Atualiza as informações de um cliente.
     * @param client Objeto Client com todas as novas informações.
     */
    @Override
    public void updateClientInformation(Client client) {

        // ainda em construção
    }

    /**
     * Atualiza o saldo bancario de uma conta especificada.
     * @param accnumber numero da conta.
     * @param value novo valor do saldo.
     * @exception DatabaseException Lançada quando ocorre um erro no banco de dados.
     */
    @Override
    public void updateBalance(String accnumber, BigDecimal value) {

        String sql  = "update conta set balanco = ? where numeroconta = ?";

        try(Connection conn = db.connect(); PreparedStatement stm = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            // realiza a operação no banco de dados
            try {
                stm.setBigDecimal(1, value);
                stm.setString(2, accnumber);
                stm.executeUpdate();

            } catch (Exception e) {
                conn.rollback();
                throw new DatabaseException("Não foi possivel realizar a operação.");
            }

            conn.commit();
        } catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Não foi possivel conectar ao banco.");
        }

    }

    /**
     * Deleta a conta especificada do banco de dados.
     * @param accNumber Numero da conta
     * @param cpf CPF do cliente
     * @exception DatabaseException Lançada quando ocorre um erro no banco de dados.
     */
    @Override
    public void deleteClient(String accNumber, CPF cpf) {


        String clientSql = "delete from cliente where cpf = ?";
        String accountSql = "delete from conta where numeroconta = ?";

        try(Connection connection = db.connect()) {
            connection.setAutoCommit(false);

            try(PreparedStatement stmAccount = connection.prepareStatement(accountSql);
                PreparedStatement stmClient = connection.prepareStatement(clientSql) ) {

                // apaga a entidade conta relacionada
                stmAccount.setString(1, accNumber);
                stmAccount.executeUpdate();

                // apaga a entidade cliente relacionada
                stmClient.setString(1, cpf.getValue());
                stmClient.executeUpdate();
            }
            catch (Exception e) {
                connection.rollback();
                throw new DatabaseException("Não foi possivel encerrar a conta.");
            }

            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
