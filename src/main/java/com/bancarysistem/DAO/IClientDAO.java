package com.bancarysistem.DAO;

import com.bancarysistem.model.CPF;
import com.bancarysistem.model.Client;

import java.math.BigDecimal;

public interface IClientDAO {


    public Client getClientByAccountNumber(String accNumber);

    public Client getClientByCpf(CPF cpf);

    public void insertClient(Client client);

    public void updateClientInformation(Client client);

    public void updateBalance(String accnumber, BigDecimal value);

    public void deleteClient(String accNumber, CPF cpf);
}
