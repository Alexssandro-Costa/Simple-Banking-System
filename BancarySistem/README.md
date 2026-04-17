# Sistema Bancario Simplificado

Este repositório foi desenvolvido como um projeto de estudo prático, focado na evolução de arquitetura de software e persistência de dados em Java.

O objetivo principal foi transitar de um modelo de armazenamento simples em arquivos para uma estrutura profissional com banco de dados relacional e, futuramente, uma interface web.

---

## Etapas do Desenvolvimento

O projeto foi construído de forma incremental, seguindo as fases abaixo:

### 1. Persistência com Java NIO.2
Implementação inicial focada na manipulação de arquivos locais. Utilizei a biblioteca `java.nio` para gerenciar a leitura e escrita de dados de forma eficiente, explorando o sistema de arquivos do SO.

### 2. Integração com Banco de Dados (JDBC)
O projeto passou por uma refatoração na camada de acesso a dados (com.bancarysistem.Repository). Substituí o armazenamento em arquivos por um banco de dados relacional, utilizando **JDBC** para gerenciar as consultas SQL e a conexão com o banco.

### 3. Interface Gráfica (Em construção)
Atualmente trabalhando na criação de uma interface web básica utilizando **HTML5, CSS3 e JavaScript** para fornecer uma experiência de usuário mais amigável e conectada ao backend.

---

## 🛠️ Tecnologias e Ferramentas

* **Linguagem Principal:** Java
* **Gerenciador de Dependências:** Maven
* **Persistência:** JDBC / PostgreSQL
* **Frontend:** HTML, CSS e JavaScript

---

## Como Executar

1. Clone o repositório:
   ```bash
   git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
---
