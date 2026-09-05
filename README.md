# Sistema Bancário Simplificado

Projeto desenvolvido como parte de um estudo orientado a projetos, com o objetivo de evoluir gradualmente uma aplicação Java desde uma solução simples baseada em arquivos até uma API REST utilizando Spring Boot.

Ao longo do desenvolvimento, o projeto passou por diversas refatorações arquiteturais, permitindo explorar conceitos de persistência de dados, orientação a objetos, APIs REST, segurança, modelagem de domínio, testes automatizados, containerização e deploy em ambiente cloud.

> **Status:** API REST desenvolvida, containerizada e publicada em ambiente cloud.

**[Acessar a API](https://sistema-bancario-simplificado.onrender.com)**

**[Acessar Swagger UI](https://sistema-bancario-simplificado.onrender.com/swagger-ui/index.html#/)**

---

## Índice

* [Funcionalidades](#funcionalidades)
* [Evolução do Projeto](#evolução-do-projeto)
* [Arquitetura](#arquitetura)
* [Modelagem de Domínio](#modelagem-de-domínio)
* [Segurança](#segurança)
* [Testes Automatizados](#testes-automatizados)
* [Containerização](#containerização)
* [Deploy](#deploy)
* [Tecnologias Utilizadas](#tecnologias-utilizadas)
* [Próximos Passos](#próximos-passos)
* [Como Executar](#como-executar)
* [Objetivo Educacional](#objetivo-educacional)
* [Documentação](#documentação)

---

## Funcionalidades

* Criação de contas bancárias
* Consulta de dados da conta
* Depósito de valores
* Saque de valores
* Transferência entre contas
* Exclusão de contas
* Registro e consulta de transações
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

### Versão 3.1 — Refatoração e Testes

Nesta etapa foram realizados ajustes estruturais e melhorias na qualidade do projeto.

Principais mudanças:

* Conclusão dos testes unitários das principais classes
* Documentação das classes relacionadas à configuração do Spring Security
* Revisão e atualização dos diagramas UML
* Criação dos diagramas entidade-relacionamento
* Refatoração do serviço de cadastro para retornar um token de acesso
* Refatoração da consulta de extrato, organizando as transações em DTOs
* Criação da classe utilitária `SearchEntityFromRepository`, centralizando a lógica de busca de entidades e tratamento das exceções correspondentes

### Versão 3.2 — Containerização

O projeto foi containerizado utilizando Docker.

Foram adicionados:

* `Dockerfile` para construção da imagem da API
* `.dockerignore`
* `docker-compose.yml`
* Arquivo `.env` para configuração das variáveis de ambiente
* Container PostgreSQL
* Volume nomeado para persistência dos dados
* Redes Docker para comunicação entre os serviços

A aplicação utiliza uma estratégia de **multi-stage build**, utilizando uma imagem Maven para compilação e uma imagem JRE mais leve para execução.

### Versão 3.3 — Deploy

A aplicação foi publicada em ambiente cloud utilizando uma imagem Docker.

Processo realizado:

1. Criação de uma imagem Docker para a API.
2. Publicação da imagem no Docker Hub.
3. Configuração de um Web Service no Render utilizando a imagem Docker.
4. Criação de um banco PostgreSQL no Render.
5. Configuração das variáveis de ambiente da aplicação.
6. Deploy da API.

A aplicação está disponível online e pode ser acessada através do Swagger UI.

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

Para uma visão mais detalhada da arquitetura e dos diagramas do projeto:

**[Ver documentação da arquitetura](ARCHITECTURE.md)**

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
3. O cliente envia o token no header `Authorization`.
4. O Spring Security valida o token.
5. O acesso aos endpoints protegidos é liberado.

Principais tecnologias:

* Spring Security
* JWT
* `AuthenticationManager`
* `UserDetails`
* `UserDetailsService`

As principais classes relacionadas à configuração do Spring Security também estão documentadas no código.

---

## Testes Automatizados

O projeto possui testes unitários para as principais regras de negócio relacionadas às operações bancárias.

Tecnologias utilizadas:

* JUnit 5
* Mockito

Casos testados incluem:

* Depósito
* Saque
* Transferência
* Validação de valores inválidos
* Tratamento de exceções

---

## Containerização

A aplicação utiliza Docker para padronizar o ambiente de execução.

A estrutura de containers é composta por:

```text
┌─────────────────────┐
│       API           │
│   Spring Boot       │
│      :8080          │
└──────────┬──────────┘
           │
           │
┌──────────▼──────────┐
│     PostgreSQL      │
│       :5432         │
└─────────────────────┘
```

O `docker-compose.yml` configura:

* Container da API
* Container PostgreSQL
* Volume para persistência dos dados
* Redes Docker
* Variáveis de ambiente

### Executar com Docker Compose

```bash
docker compose up --build
```

Após a inicialização, a API estará disponível em:

```text
http://localhost:8080
```

---

## Deploy

A aplicação está publicada utilizando uma imagem Docker.

### Tecnologias utilizadas no deploy

* Docker
* Docker Hub
* Render
* PostgreSQL

### Acesso

**API:**

https://sistema-bancario-simplificado.onrender.com

**Swagger UI:**

https://sistema-bancario-simplificado.onrender.com/swagger-ui/index.html#/

> A aplicação atualmente utiliza o Swagger UI como interface para interação com a API. Uma interface web própria está planejada para uma etapa futura.

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

### Containerização e Deploy

* Docker
* Docker Compose
* Docker Hub
* Render

### Versionamento

* Git
* GitHub

---

## Próximos Passos

* Desenvolvimento da interface web
* Documentação completa da API com Swagger/OpenAPI
* Ampliação da cobertura de testes
* Melhorias de observabilidade e logging
* Evolução da arquitetura conforme novas funcionalidades forem adicionadas

---

## Como Executar

### Pré-requisitos

* Java 21
* Maven
* PostgreSQL

### Clonar o repositório

```bash
git clone https://github.com/Alexssandro-Costa/Simple-Banking-System.git
cd Simple-Banking-System
```

### Executar utilizando Docker

A forma recomendada de executar o projeto é utilizando Docker Compose:

```bash
docker compose up --build
```

### Executar localmente

Configure as variáveis de ambiente necessárias para conexão com o banco de dados e execute:

```bash
mvn spring-boot:run
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
* Containerização
* Deploy
* Desenvolvimento Backend com Spring Boot

---

## Documentação

Documentação complementar do projeto:

* [Arquitetura da Aplicação](ARCHITECTURE.md)
* [Swagger UI](https://sistema-bancario-simplificado.onrender.com/swagger-ui/index.html#/)
