package com.project.simple_banking_system.config.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.project.simple_banking_system.exceptions.AccountNotFoundException;
import com.project.simple_banking_system.model.valueObjects.Cpf;
import com.project.simple_banking_system.repository.ClientRepository;


@Service
public class AuthConfig implements UserDetailsService{

    @Autowired
    ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
     
        return clientRepository.findByCpf(new Cpf(username)).orElseThrow(() -> new AccountNotFoundException(username));
    }
    
}
