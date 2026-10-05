package com.project.simple_banking_system.config.springSecurity;

/**
 * Um Record que representa os dados de identificação do usuário extraídos ou inseridos em um token JWT.
 * <p>
 * Esta estrutura imutável é utilizada como um Objeto de Transferência de Dados (DTO) para
 * transportar de forma leve as informações essenciais do cliente autenticado dentro do contexto de segurança.
 * </p>
 *
 * @param id  O identificador único universal (UUID) do cliente, que atua como a chave primária no banco de dados.
 * @param cpf O Cadastro de Pessoas Físicas (CPF) do cliente, utilizado como o username no sistema.
 * @author Alexssandro
 * @version 1.0
 */
public record JWTUserData(String id, String cpf ) {
}
