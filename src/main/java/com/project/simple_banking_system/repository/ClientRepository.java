package com.project.simple_banking_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.simple_banking_system.model.entity.Client;




/**
 * interface Repository que define metodos de acesso e manipulação de uma entidade Client
 * @author Alexssandro
 * @since release 3
 * @version 1
 */
public interface ClientRepository extends JpaRepository<Client, UUID> {}
