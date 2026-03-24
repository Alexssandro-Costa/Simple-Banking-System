package service;

import Repository.DBManager;
import exceptions.DatabaseException;
import model.Account;
import model.CPF;
import model.Client;
import model.Password;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.sql.*;


public class DataService {

    private static DBManager manager;

    DataService() {
        manager = new DBManager();
    }

    public Client select(String identification, String password) {

        /// Busca e retorna os dados da conta relacionado ao id informado

        String sql = "SELECT c.nome, c.cpf, c.telefone, c.datanascimento, c.senha, " +
                "ct.numeroconta, ct.balanco " +
                "FROM cliente c " +
                "JOIN conta ct ON c.id = ct.cliente_id " +
                "WHERE ct.numeroconta = ?";

        try (Connection conn = manager.connect(); PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, identification);
            ResultSet rs = stmt.executeQuery();

            // retorna a conta relacionada ao identificador e senha passados
            if(rs.next()) {
                if(Password.compare(rs.getString("senha"), password)) {
                    return new Client(rs.getString("nome"), rs.getString("numeroconta"), rs.getBigDecimal("balanco"));
                }
                else throw new AccessDeniedException("CPF ou Senha incorretos");
            }
        } catch (Exception e) {
            // enviar log(ainda não feito)
            e.printStackTrace();
            throw new DatabaseException("ERRO! Não foi possivel realizar a operação no banco", e);
        }

        return null;
    }


    public void insert(Client c) {

        ///  insere as informações de um novo cliente no banco de dados

        String clientSql = "insert into cliente(nome, cpf, telefone, datanascimento, senha) values (?, ?, ?, ?, ?)";
        String accountSql = "insert into conta(cliente_id, numeroconta, balanco) values (?,?, ?)";

        try(Connection conn = manager.connect()) {
            conn.setAutoCommit(false);

            // Insere os dados na tabela cliente
            try(PreparedStatement stm1 = conn.prepareStatement(clientSql, Statement.RETURN_GENERATED_KEYS)) {

                stm1.setString(1, c.getName());
                stm1.setString(2, c.getCPF().getValue());
                stm1.setString(3, c.getPhone().getNumber());
                stm1.setDate(4, Date.valueOf(c.getDTBirth()));
                stm1.setString(5, c.getPassword().getValue());
                stm1.executeUpdate();

                // recupera o id criado pelo banco
                try (ResultSet rs = stm1.getGeneratedKeys()) {

                    if(rs.next()) {
                        int id = rs.getInt(1);

                        // insere os dados na tabela conta
                        try (PreparedStatement stm2 = conn.prepareStatement(accountSql)) {
                            stm2.setInt(1, id);
                            stm2.setString(2, c.getAccount().getAccountNumber());
                            stm2.setBigDecimal(3, c.getAccount().getBalance());
                            stm2.executeUpdate();
                        }
                    }
                    else throw new DatabaseException("Não foi possivel salvar os dados no banco");
                }
            } catch (SQLException e) {
                conn.rollback();
                throw new DatabaseException("Não foi possivel salvar os dados no banco");
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
