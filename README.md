# SYMBANK

Sistema bancário desenvolvido como um projeto de estudo orientado a projetos, com o objetivo de evoluir gradualmente uma aplicação Java desde uma solução simples baseada em arquivos até uma aplicação web full-stack.

Ao longo do desenvolvimento, o projeto passou por diversas refatorações arquiteturais e incrementos tecnológicos, explorando conceitos de **Programação Orientada a Objetos, persistência de dados, APIs REST, segurança, modelagem de domínio, testes automatizados, desenvolvimento web, containerização e deploy em ambiente cloud**.

O projeto atualmente é composto por uma **API REST desenvolvida com Spring Boot** e uma **interface web desenvolvida com HTML, CSS e JavaScript**, executadas em containers Docker independentes.

> **Status:** Aplicação web full-stack desenvolvida, containerizada e publicada em ambiente cloud.

**[Acessar o SYMBANK](https://symbank.onrender.com)**

**[Acessar a API](https://sistema-bancario-simplificado.onrender.com)**

**[Acessar Swagger UI](https://sistema-bancario-simplificado.onrender.com/swagger-ui/index.html#/)**

---

## Índice

* [Funcionalidades](#funcionalidades)
* [Evolução do Projeto](#evolução-do-projeto)
* [Arquitetura](#arquitetura)
* [Modelagem de Domínio](#modelagem-de-domínio)
* [Frontend](#frontend)
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

### Autenticação

* Cadastro de usuários
* Login
* Autenticação através de JWT
* Armazenamento do token durante a sessão
* Encerramento da sessão

### Operações bancárias

* Criação de contas bancárias
* Consulta de dados da conta
* Depósito de valores
* Saque de valores
* Transferência entre contas
* Exclusão de contas
* Registro e consulta de transações
* Consulta de extrato

### Interface Web

* Tela de login
* Tela de cadastro
* Página inicial da conta
* Dashboard com visualização de dados
* Interface para operações bancárias
* Interface para consulta de extrato
* Formatação e validação de dados de entrada
* Navegação entre operações sem recarregamento da página

---

## Evolução do Projeto

### Versão 1 — Persistência em Arquivos

A primeira versão utilizava armazenamento local através da API Java NIO.2.

As contas eram salvas em arquivos texto e posteriormente reconstruídas em objetos durante a leitura dos dados.

Principais conceitos estudados:

* Manipulação de arquivos com Java NIO.2
* Serialização manual de dados
* Programação Orientada a Objetos
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
* Autenticação JWT
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

### Versão 3.3 — Deploy da API

A API foi publicada em ambiente cloud utilizando uma imagem Docker.

Processo realizado:

1. Criação de uma imagem Docker para a API.
2. Publicação da imagem no Docker Hub.
3. Configuração de um Web Service no Render utilizando a imagem Docker.
4. Configuração do banco de dados PostgreSQL.
5. Configuração das variáveis de ambiente da aplicação.
6. Deploy da API.

### Versão 4 — Desenvolvimento da Interface Web

A versão 4 iniciou a transformação do projeto em uma aplicação web completa.

Foi desenvolvida uma interface própria utilizando **HTML, CSS e JavaScript**, responsável por consumir a API REST e disponibilizar as funcionalidades bancárias através do navegador.

#### Autenticação

Foram desenvolvidas as telas de login e cadastro.

O frontend passou a realizar:

* Validação dos dados inseridos
* Formatação automática do CPF
* Conversão dos dados dos formulários para JSON
* Envio de requisições HTTP para a API
* Redirecionamento após autenticação
* Armazenamento do token JWT durante a sessão

A comunicação com a API foi organizada em funções reutilizáveis, incluindo uma função responsável por converter formulários HTML em JSON e outra responsável por centralizar as requisições de autenticação.

O código JavaScript também foi separado em módulos e arquivos independentes, buscando separar a estrutura HTML da lógica da aplicação.

#### Página da Conta

Foi criada uma página principal para a conta do usuário, contendo:

* Dados da conta
* Barra de navegação
* Área de operações
* Área de extrato
* Dashboard

A aplicação utiliza `sessionStorage` para manter o token JWT durante a sessão atual.

Também foram criadas funções assíncronas reutilizáveis para realizar requisições autenticadas à API.

#### Operações Bancárias

As operações de depósito, saque e transferência foram integradas à interface.

A aplicação utiliza uma única página para as operações, modificando dinamicamente o conteúdo da seção principal de acordo com a operação selecionada.

Os formulários de transação também são adaptados dinamicamente conforme o tipo de operação.

Foi criada uma função `sendTransaction` para centralizar os elementos comuns às requisições de transações e uma função `showTransactionData` para apresentar os dados retornados pela API.

#### Extrato

Foi adicionada uma seção para consulta do extrato da conta.

As funções responsáveis pela consulta e apresentação dos dados foram separadas da estrutura da página, permitindo reutilização da lógica de comunicação com a API.

#### Dashboard

A página inicial da conta passou a possuir uma área destinada à visualização gráfica dos dados das transações.

Foi utilizada a biblioteca **Chart.js** para gerar os gráficos.

Os dados do extrato são processados pelo frontend antes de serem utilizados para construir as visualizações.

#### Configuração do Frontend

O frontend foi estruturado para receber a URL da API através de uma variável de ambiente durante a inicialização do container.

Como as variáveis definidas no Docker Compose não ficam diretamente disponíveis no JavaScript executado pelo navegador, foi utilizado um arquivo de configuração baseado em template.

O `envsubst` é utilizado durante a inicialização do container para gerar o arquivo de configuração final a partir do template.

#### Containerização do Frontend

O frontend foi containerizado utilizando **Nginx** como servidor web.

O container utiliza uma imagem `nginx:alpine` e disponibiliza os arquivos HTML, CSS e JavaScript através da porta 80.

#### Integração entre Frontend e API

Como frontend e backend são executados em origens diferentes, foi adicionada uma configuração de **CORS** ao Spring Security.

A API passou a permitir as origens utilizadas pelo ambiente local e pelo frontend publicado.

#### Deploy

A primeira versão estável do frontend foi publicada utilizando uma imagem Docker hospedada no Docker Hub.

O frontend está disponível em:

**https://symbank.onrender.com**

O banco de dados PostgreSQL utilizado pela aplicação foi posteriormente migrado do Render para o **Neon**, buscando uma solução de banco de dados com uma política mais adequada ao uso contínuo do projeto em seu plano gratuito.

#### Rebranding

Durante a versão 4, o projeto passou a utilizar o nome **SYMBANK**, uma combinação de **Simple + Bank**.

---

## Arquitetura

Atualmente, o projeto é dividido em três componentes principais:

```text
                    ┌──────────────────────┐
                    │       Browser        │
                    │                      │
                    │  HTML / CSS / JS     │
                    └──────────┬───────────┘
                               │
                               │ HTTP / JSON
                               ▼
                    ┌──────────────────────┐
                    │         API          │
                    │    Spring Boot       │
                    │                      │
                    │ Controller           │
                    │      ↓               │
                    │ Service              │
                    │      ↓               │
                    │ Repository           │
                    └──────────┬───────────┘
                               │
                               │ JPA
                               ▼
                    ┌──────────────────────┐
                    │     PostgreSQL       │
                    │       Neon           │
                    └──────────────────────┘
```

### Backend

A API segue uma arquitetura baseada em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Banco de Dados
```

#### Controller

Responsável por receber as requisições HTTP e retornar as respostas da API.

#### Service

Contém as regras de negócio da aplicação.

#### Repository

Responsável pelo acesso aos dados através do Spring Data JPA.

#### Domain

Contém as entidades e objetos de valor que representam o domínio bancário.

### Frontend

O frontend é responsável pela interface gráfica e pela comunicação com a API.

Sua estrutura utiliza JavaScript modularizado, separando responsabilidades como:

* Autenticação
* Gerenciamento de sessão
* Requisições HTTP
* Operações bancárias
* Extrato
* Dashboard
* Configuração da aplicação

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

A autenticação da aplicação é realizada através de **JWT (JSON Web Token)**.

Fluxo de autenticação:

1. Usuário realiza login através do frontend.
2. O frontend envia as credenciais para a API.
3. A API autentica o usuário.
4. A aplicação gera um token JWT.
5. O frontend armazena o token durante a sessão.
6. O token é enviado no header `Authorization` nas requisições protegidas.
7. O Spring Security valida o token.
8. O acesso aos endpoints protegidos é liberado.

Principais tecnologias e componentes:

* Spring Security
* JWT
* `AuthenticationManager`
* `UserDetails`
* `UserDetailsService`

A API também possui configuração de **CORS** para permitir a comunicação com o frontend hospedado em uma origem diferente.

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

O projeto utiliza Docker para padronizar os ambientes de execução.

A aplicação é composta por containers independentes para os principais componentes:

```text
┌─────────────────────┐
│       Frontend      │
│       Nginx         │
│        :80          │
└──────────┬──────────┘
           │
           │ HTTP
           ▼
┌─────────────────────┐
│         API         │
│    Spring Boot      │
│       :8080         │
└──────────┬──────────┘
           │
           │ PostgreSQL
           ▼
┌─────────────────────┐
│     PostgreSQL      │
│        Neon         │
└─────────────────────┘
```

### Frontend

O frontend utiliza:

* Nginx
* `nginx:alpine`
* `envsubst`
* Dockerfile próprio

### Backend

A API utiliza:

* Docker
* Multi-stage build
* Maven
* Java 21
* Spring Boot

### Desenvolvimento local

O `docker-compose.yml` pode ser utilizado para iniciar os serviços da aplicação em ambiente local.

```bash
docker compose up --build
```

---

## Deploy

O projeto possui frontend e backend publicados separadamente.

### Frontend

O frontend é executado através de uma imagem Docker publicada no Docker Hub e hospedada no Render.

**[Acessar o SYMBANK](https://symbank.onrender.com)**

### Backend

A API é executada através de uma imagem Docker publicada no Docker Hub e hospedada no Render.

**[Acessar a API](https://sistema-bancario-simplificado.onrender.com)**

**[Acessar Swagger UI](https://sistema-bancario-simplificado.onrender.com/swagger-ui/index.html#/)**

### Banco de Dados

O PostgreSQL utilizado pela aplicação está hospedado no **Neon**.

A separação entre frontend, API e banco de dados permite que cada componente seja hospedado e configurado independentemente.

---

## Tecnologias Utilizadas

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT
* Maven

### Frontend

* HTML5
* CSS3
* JavaScript
* Fetch API
* Chart.js

### Banco de Dados

* PostgreSQL
* Neon

### Testes

* JUnit 5
* Mockito

### Containerização

* Docker
* Docker Compose
* Nginx
* Docker Hub

### Deploy

* Render
* Neon

### Versionamento

* Git
* GitHub

---

## Próximos Passos

* Melhorias na interface e experiência do usuário
* Ampliação do dashboard
* Ampliação da cobertura de testes
* Melhorias de observabilidade e logging
* Evolução da arquitetura conforme novas funcionalidades forem adicionadas
* Melhorias na estratégia de gerenciamento da sessão e armazenamento do token
* Evolução da documentação da API com Swagger/OpenAPI

---

## Como Executar

### Pré-requisitos

* Java 21
* Maven
* Docker
* Docker Compose

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

Após a inicialização, os serviços estarão disponíveis nas portas configuradas pelo `docker-compose.yml`.

### Executar o backend localmente

Configure as variáveis de ambiente necessárias para conexão com o banco de dados e execute:

```bash
mvn spring-boot:run
```

### Executar o frontend

O frontend pode ser executado através do container Nginx definido no Docker Compose.

---

## Objetivo Educacional

O projeto foi desenvolvido com foco em aprendizado prático e evolução contínua de conhecimentos em:

* Programação Orientada a Objetos
* Persistência de Dados
* Bancos de Dados Relacionais
* APIs REST
* Arquitetura de Software
* Segurança de Aplicações
* Autenticação e autorização
* Desenvolvimento Web
* JavaScript
* Comunicação entre frontend e backend
* Testes Automatizados
* Containerização
* Deploy em ambiente cloud
* Desenvolvimento Backend com Spring Boot

Além do desenvolvimento das funcionalidades, o projeto foi utilizado para experimentar diferentes abordagens arquiteturais e evoluir gradualmente uma aplicação à medida que novos conhecimentos eram adquiridos.

---

## Documentação

Documentação complementar do projeto:

* [Arquitetura da Aplicação](ARCHITECTURE.md)
* [Swagger UI](https://sistema-bancario-simplificado.onrender.com/swagger-ui/index.html#/)
