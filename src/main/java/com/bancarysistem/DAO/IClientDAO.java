package com.bancarysistem.DAO;

import com.bancarysistem.model.CPF;
import com.bancarysistem.model.Client;
import java.math.BigDecimal;


/**
 * Interface que define métodos de inserção, atualização, busca e exclusão de dados.
 * @author Alexssandro
 * @since release 2
 * @version 1.1
 */
public interface IClientDAO {


    public Client getClientByAccountNumber(String accNumber);

    public Client getClientInformation(CPF cpf);

    public void insertClient(Client client);

    public void updateClientInformation(Client client);

    public void updateBalance(String accnumber, BigDecimal value);

    public void deleteClient(String accNumber, CPF cpf);
}
