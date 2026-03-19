package service;

import Repository.DBManager;
import exceptions.AccountNotFoundException;
import exceptions.DatabaseException;
import model.Account;
import model.Client;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DataService {

    private DBManager manager;

    DataService() {
        manager = new DBManager();
    }

    public void setClient(Client c) throws SQLException {

        ///  Transforma o DTO em uma classe completa e a envia pro DBManager

        try{
            manager.insert(c);
        } catch (Exception e) {
            throw new DatabaseException("ERRO! Não foi possivel salvar os dados inseridos");
        }
    }

    public Client getClient(String cpf, String password) {

        ///  Recupera os dados do Repositorio e os retorna

        try {
            return manager.search(cpf, password);
        }catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

    }

}
