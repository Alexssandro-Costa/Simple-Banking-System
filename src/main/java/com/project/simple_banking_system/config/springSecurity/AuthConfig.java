package com.project.simple_banking_system.config.springSecurity;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.repository.ClientRepository;


/**
 * Serviço de autenticação customizado para o Spring Security.
 * <p>
 * Esta classe implementa a interface {@link UserDetailsService} para permitir que o Spring Security
 * busque os dados do cliente (usuário) no banco de dados durante o processo de autenticação,
 * utilizando o CPF como identificador único (username).
 * </p>
 *
 * @author Alexssandro
 * @version 1.0
 */
@Service
public class AuthConfig implements UserDetailsService{

    /**
     * Repositório para comunicação e consultas à entidade Cliente no banco de dados.
     */
    @Autowired
    ClientRepository clientRepository;

    /**
     * Localiza um usuário no banco de dados com base no identificador fornecido (Username).
     * <p>
     * No contexto deste sistema bancário, o "username" esperado é o CPF do cliente. O método
     * encapsula a String recebida em um Value Object {@link Cpf} para realizar a busca de forma segura.
     * </p>
     *
     * @param username O CPF do cliente que está tentando se autenticar (enviado como String).
     * @return Os detalhes do usuário como uma instância de {@link UserDetails}.
     * @throws AccountNotFoundException Se nenhum cliente for encontrado com o CPF informado.
     */
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        // Instancia o Value Object Cpf a partir da String e busca no repositório.
        // Caso o Optional retornado esteja vazio, lança a exceção personalizada de conta não encontrada.
        return clientRepository.findByCpf(new Cpf(username)).orElseThrow(
                () -> new AccountNotFoundException(username));
    }
    
}
