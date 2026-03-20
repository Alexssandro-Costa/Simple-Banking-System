package service;

import Repository.DBManager;
import exceptions.DatabaseException;
import model.Account;
import model.Client;

import java.math.BigDecimal;
import java.sql.*;


public class DataService {

    private DBManager manager;

    DataService() {
        manager = new DBManager();
    }

    public Client select(String cpf, String password) {

        /// Busca e retorna os dados da conta relacionado ao id informado

        String sql = "SELECT * FROM CLIENTE c join CONTA ct on c.cpf = ct.idcliente WHERE c.cpf = ? and c.senha = ?";

        try (Connection conn = manager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new Client(rs.getString("nome"), rs.getString("cpf"), rs.getString("telefone"),
                        rs.getDate("datanascimento").toLocalDate(), rs.getString("senha"),
                        new Account(rs.getString("numeroconta"), rs.getBigDecimal("balanco")));
            }
        } catch (Exception e) {
            // enviar log(ainda não feito)
            throw new DatabaseException("Não foi possivel acessar a conta buscada", e);
        }

        return null;
    }


    public void insert(Client c) {

        ///  insere as informações de um novo cliente no banco de dados

        String clientSql = "insert into cliente(nome, cpf, telefone, datanascimento, senha) values (?, ?, ?, ?, ?)";
        String accountSql = "insert into conta(idcliente, numeroconta, balanco) values (?,?, ?)";

        try(Connection conn = manager.connect()) {
            conn.setAutoCommit(false);

            // Insere os dados na tabela cliente
            try(PreparedStatement stm1 = conn.prepareStatement(clientSql)) {

                stm1.setString(1, c.getName());
                stm1.setString(2, c.getCPF());
                stm1.setString(3, c.getPhone().getNumber());
                stm1.setDate(4, Date.valueOf(c.getDTBirth()));
                stm1.setString(5, c.getPassword().getValue());
                stm1.executeUpdate();
            }catch (SQLException e) {
                conn.rollback();
                // enviar log(ainda não feito)
                throw new DatabaseException("Não foi possivel salvar os dados da conta");
            }
            // insere os dados na tabela conta
            try(PreparedStatement stm2 = conn.prepareStatement(accountSql)) {
                stm2.setString(1, c.getCPF());
                stm2.setString(2, c.getAccount().getAccountNumber());
                stm2.setBigDecimal(3, c.getAccount().getBalance());
                stm2.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
                // enviar log(ainda não feito)
                throw new DatabaseException("Não foi possivel salvar os dados da conta");
            }

            conn.commit();
        }
        catch (Exception e) {
            throw new DatabaseException("ERRO! Dados da conta não puderam ser salvos");

        }
    }


    public void update(String accountNumber, BigDecimal value) {

        ///  Realiza uma alteração do valor de saldo bancario na coluna balanco pertence ao numero da conta passado

        String sql  = "UPDATE conta SET balanco = balanco + ? WHERE NUMEROCONTA = ?";

        try (Connection conn = manager.connect()) {
            conn.setAutoCommit(false);

            try(PreparedStatement stm = conn.prepareStatement(sql)) {
                stm.setBigDecimal(1, value);
                stm.setString(2, accountNumber);
                stm.executeUpdate();

            }catch (SQLException e) {
                conn.rollback();
                // enviar log(ainda não feito)
                throw new DatabaseException("mão foi possivel encontrar a conta especificada");
            }

            conn.commit();
        } catch (Exception e) {
            throw new DatabaseException("Não foi possivel realizar a transação");
        }
    }


    public void update(String accountNumber, String accountTarget, BigDecimal value) {

        ///  Realiza uma transferencia de valor entre duas contas passadas

        String sql  = "UPDATE conta SET balanco = balanco + ? WHERE NUMEROCONTA = ?";

        try (Connection conn = manager.connect()) {
            conn.setAutoCommit(false);

            // realiza um retirada de valor da conta remetente
            try(PreparedStatement stm = conn.prepareStatement(sql)) {
                stm.setBigDecimal(1, value.negate());
                stm.setString(2, accountNumber);
                stm.executeUpdate();

            }catch (SQLException e) {
                conn.rollback();
                // enviar log(ainda não feito)
                throw new DatabaseException("mão foi possivel encontrar a conta especificada");
            }
            // realiza uma inserção de valor na conta destinataria
            try(PreparedStatement stm = conn.prepareStatement(sql)) {
                stm.setBigDecimal(1, value);
                stm.setString(2, accountTarget);
                stm.executeUpdate();

            }catch (SQLException e) {
                conn.rollback();
                // enviar log(ainda não feito)
                throw new DatabaseException("mão foi possivel encontrar a conta especificada");
            }

            conn.commit();
        } catch (Exception e) {
            throw new DatabaseException("Não foi possivel realizar a transação");
        }


    }




}
