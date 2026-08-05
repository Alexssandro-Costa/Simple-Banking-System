# Sistema Bancário Simplificado

Projeto desenvolvido como parte de um estudo orientado a projetos, com o objetivo de evoluir gradualmente uma aplicação Java desde uma solução simples baseada em arquivos até uma API REST moderna utilizando Spring Boot.

Ao longo do desenvolvimento, o projeto passou por diversas refatorações arquiteturais, permitindo explorar conceitos fundamentais de persistência de dados, orientação a objetos, APIs REST, segurança, modelagem de domínio e testes automatizados.

---

## Índice
* [Funcionalidades](#Funcionalidades)
* [Evolução do Projeto](#Evolução-do-Projeto)
* [Arquitetura](#Arquitetura)
* [Modelagem de Domínio](#Modelagem_de_Domínio)
* [Segurança](#Segurança)
* [Testes Automatizados](#Testes-Automatizados)
* [Tecnologias Utilizadas](#Tecnologias-Utilizadas)
* [Próximos Passos](#Próximos-Passos)
* [Como Executar](#Como-Executar)
* [Objetivo Educacional](#Objetivo-Educacional)
* [Documentação](#Documentação)

---

## Funcionalidades

* Criação de contas bancárias
* Consulta de dados da conta
* Depósito de valores
* Saque de valores
* Transferência entre contas
* Exclusão de contas
* Registro de transações
* Autenticação de usuários
* Autorização baseada em JWT

---

## Evolução do Projeto

### Versão 1 — Persistência em Arquivos

A primeira versão utilizava armazenamento local através da API Java NIO.2.

As contas eram salvas em arquivos texto e posteriormente reconstruídas em objetos durante a leitura dos dados.

Principais conceitos estudados:

* Manipulação de arquivos com Java NIO.2
* Serialização manual de dados
* Programação orientada a objetos
* Interface via terminal

---

### Versão 2 — Banco de Dados Relacional

A segunda versão substituiu completamente a persistência em arquivos por um banco de dados PostgreSQL utilizando JDBC.

Também foram adicionadas novas funcionalidades e melhorias estruturais.

Principais mudanças:

* Migração para PostgreSQL
* Implementação da camada DAO
* Operações de transferência entre contas
* Exclusão de contas
* Criptografia de senhas
* Refatoração do modelo de domínio

Tecnologias utilizadas:

* JDBC
* PostgreSQL
* Maven

---

### Versão 3 — API REST com Spring Boot

A terceira versão transformou o sistema em uma API REST.

A camada DAO foi substituída pelos repositórios do Spring Data JPA, simplificando o acesso aos dados e permitindo uma modelagem mais rica do domínio.

Principais melhorias:

* Spring Boot
* Spring Data JPA
* Spring Security
* JWT Authentication
* Tratamento global de exceções
* Arquitetura em camadas
* Value Objects
* Relacionamentos JPA

---

## Arquitetura

A aplicação segue uma arquitetura baseada em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Banco de Dados
```

### Camadas

#### Controller

Responsável por receber as requisições HTTP e retornar respostas apropriadas.

#### Service

Contém as regras de negócio da aplicação.

#### Repository

Responsável pelo acesso aos dados através do Spring Data JPA.

#### Domain

Contém as entidades e objetos de valor que representam o domínio bancário.

---

## Modelagem do Domínio

### Entidades

* Cliente
* Conta
* Transação

### Relacionamentos

* Cliente ↔ Conta (1:1)
* Conta ↔ Transação (1:N)

Anotações utilizadas:

* `@Entity`
* `@Table`
* `@OneToOne`
* `@OneToMany`
* `@ManyToOne`
* `@JoinColumn`

---

## Segurança

A autenticação da aplicação é realizada através de JWT (JSON Web Token).

Fluxo:

1. Usuário realiza login.
2. A aplicação gera um token JWT.
3. O cliente envia o token no header Authorization.
4. O Spring Security valida o token.
5. O acesso aos endpoints protegidos é liberado.

Principais tecnologias:

* Spring Security
* JWT
* AuthenticationManager
* UserDetails
* UserDetailsService

---

## Testes Automatizados

O projeto possui testes unitários para as regras de negócio relacionadas às operações bancárias.

Tecnologias utilizadas:

* JUnit 5
* Mockito

Casos testados:

* Depósito
* Saque
* Transferência
* Validação de valores inválidos
* Tratamento de exceções

---

## Tecnologias Utilizadas

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT
* Maven

### Banco de Dados

* PostgreSQL

### Testes

* JUnit 5
* Mockito

### Frontend (Planejado)

* HTML
* CSS
* JavaScript

---

## Próximos Passos

* Desenvolvimento da interface web
* Documentação da API com Swagger/OpenAPI
* Ampliação da cobertura de testes
* Dockerização da aplicação
* Deploy em ambiente cloud

---

## Como Executar

### Clonar o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

### Configurar o banco de dados

Crie um banco PostgreSQL e ajuste as configurações em:

```properties
application.properties
```

### Executar a aplicação

```bash
mvn spring-boot:run
```

Ou:

```bash
mvn clean install
java -jar target/nome-da-aplicacao.jar
```

---

## Objetivo Educacional

Este projeto foi desenvolvido com foco em aprendizado prático e evolução contínua de conhecimentos em:

* Programação Orientada a Objetos
* Persistência de Dados
* APIs REST
* Arquitetura de Software
* Segurança de Aplicações
* Testes Automatizados
* Desenvolvimento Backend com Spring Boot

---

## Documentação

Documentação complementar do projeto:

- [Arquitetura da Aplicação](ARCHITECTURE.md)
- Documentação da API (Em desenvolvimento)
