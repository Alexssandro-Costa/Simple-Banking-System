package com.bancarysistem.DAO;

import com.bancarysistem.Repository.DataBaseSource;
import com.bancarysistem.exceptions.AccountNotFoundException;
import com.bancarysistem.exceptions.DatabaseException;
import com.bancarysistem.model.CPF;
import com.bancarysistem.model.Client;
import com.bancarysistem.model.Password;
import org.postgresql.util.PSQLException;

import java.math.BigDecimal;
import java.sql.*;

public class ClientDAO implements IClientDAO {

    private static DataBaseSource db;

    public ClientDAO() {
        db = new DataBaseSource();
    }

    @Override
    public Client getClientByAccountNumber(String accNumber) {

        /*
            /* Procura no banco a entidade relacionada ao numero de conta passado.
            /* @return Retorna um objeto cliente Instanciado com o nome e a conta Bancaria relacionada.

        */

        String sql = "select c.nome, c.senha, ct.balanco, ct.numeroconta " +
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
                    rs.getString("numeroconta"),
                    rs.getBigDecimal("balanco"),
                    new Password(rs.getString("senha")));

        } catch (SQLException e) {
            throw new DatabaseException("Não foi possivel conectar ao banco.");
        } catch (Exception e) {
            throw new AccountNotFoundException("Não foi possivel encontrar a conta especificada.");
        }

    }

    @Override
    public Client getClientByCpf(CPF cpf) {

        /*
             /* Procura no banco a entidade relacionada ao CPF passado
             /* @return retorna um objeto cliente instanciado com todas informações.
         */

        String sql = "select c.nome, c.cpf, c.telefone, c.datanascimento, c.senha " +
                "ct.balanco, ct.numeroconta " +
                "from cliente c " +
                "join conta ct on c.id = ct.cliente_id " +
                "where ct.cpf = ?";

        try(Connection conn = db.connect(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, cpf.getValue()); // passa o cpf pro comando sql
            ResultSet rs = stm.executeQuery(); // dados recuperados do banco

            rs.next();

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

    @Override
    public void insertClient(Client client) {

        // bugged
        /*
            /* Insere um novo cliente no banco de dados
         */

        String clientSql = "insert into cliente(nome, cpf, telefone, datanascimento, senha) values (?, ?, ?, ?, ?)";
        String accountSql = "insert into conta(cliente_id, numeroconta, balanco) values (?,?, ?)";

        try(Connection conn = db.connect()) {
            conn.setAutoCommit(false);

            // insere os dados na tabela cliente
            try(PreparedStatement stm = conn.prepareStatement(clientSql, Statement.RETURN_GENERATED_KEYS)) {

                stm.setString(1, client.getName());
                stm.setString(2, client.getCPF().getValue());
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
                e.printStackTrace();
                throw new DatabaseException("Não foi possivel salvar os dados da conta");
            }

            conn.commit(); // salva o estado do banco
        }catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Não foi possivel conectar ao banco.");
        }
    }

    @Override
    public void updateClientInformation(Client client) {

        // ainda em construção
    }

    @Override
    public void updateBalance(String accnumber, BigDecimal value) {

        /*
            Realiza uma alteração no saldo bancario da entidade pertencente ao numero de conta passado.
         */
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

    @Override
    public void deleteClient(Client c) {

        /*
            Remove a conta de um cliente do sistema da empresa
         */

        String clientSql = "delete from cliente where cpf = ?";
        String accountSql = "delete from conta where numeroconta = ?";

        try(Connection connection = db.connect()) {
            connection.setAutoCommit(false);

            // apaga a entidade conta relacionada
            try(PreparedStatement stm = connection.prepareStatement(accountSql)) {
                stm.setString(1, c.getAccount().getAccountNumber());
                stm.executeUpdate();

                // apaga a entidade cliente relacionada
                try(PreparedStatement stm2 = connection.prepareStatement(clientSql)) {
                    stm2.setString(1, c.getCPF().getValue());
                    stm.executeUpdate();
                }
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
