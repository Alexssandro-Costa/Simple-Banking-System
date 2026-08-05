# Arquitetura do Sistema

Este documento reúne os principais diagramas utilizados na modelagem do Sistema Bancário Simplificado.

Os diagramas descrevem o comportamento da aplicação, a estrutura das classes e o modelo de dados utilizado.

## Índice

- Diagrama de Casos de Uso
- Diagramas de Classes
- Modelo de Dados
- Fluxo da Aplicação

---

# Diagrama de Casos de Uso

## Objetivo

Representar as funcionalidades disponíveis para o usuário e as condições necessárias para sua execução.

![Diagrama de casos de uso_02-07-2026](https://github.com/Alexssandro-Costa/Simple-Banking-System/blob/main/docs/images/UseCase.jpg)


### Principais casos de uso

- Acessar conta
- Visualizar extrato
- Realizar operações bancárias
- Alterar status da conta
- Encerrar sessão

### Pré-condições

| Caso de uso | Pré-condição |
|-------------|--------------|
| Acessar conta | Conta cadastrada |
| Visualizar extrato | Usuário autenticado |
| Realizar operação | Usuário autenticado |
| Alterar status | Usuário autenticado |
| Sair da conta | Usuário autenticado |

---

# Diagramas de Classes

Os diagramas abaixo mostram a organização da aplicação em diferentes módulos.

## Camada de Controle

Responsável por receber requisições HTTP e encaminhá-las para a camada de serviço.

![Diagrama de Classes_Controle - 05-08-2026](https://github.com/Alexssandro-Costa/Simple-Banking-System/blob/main/docs/images/Classes_Controllers.jpg)
---

## Serviço
Contém as regras de negócio da aplicação.
![Diagrama de Classes_Servico - 05-08-2026](https://github.com/Alexssandro-Costa/Simple-Banking-System/blob/main/docs/images/Classes_Services.jpg)

## Modelos de Domínio
Representa as entidades e objetos de valor do sistema bancário.
![Diagrama de Classes_Dominio - 05-08-2026](https://github.com/Alexssandro-Costa/Simple-Banking-System/blob/main/docs/images/Classes_Model.jpg)

## Utilitários
Classes auxiliares responsáveis por funcionalidades compartilhadas entre diferentes partes da aplicação.
![Diagrama de Classes_Util - 05-08-2026](https://github.com/Alexssandro-Costa/Simple-Banking-System/blob/main/docs/images/Classes_util.jpg)

# Modelo de Dados

## Modelo Conceitual

Representa as entidades do domínio e seus relacionamentos em um nível de abstração independente do banco de dados.

![DER_Conceitual - 05-08-2026](https://github.com/Alexssandro-Costa/Simple-Banking-System/blob/main/docs/images/DER_Conceitual.JPG)

---

## Modelo Lógico
Representa como o modelo conceitual foi adaptado para implementação em banco de dados relacional.

![DER_Logico - 05-08-2026](https://github.com/Alexssandro-Costa/Simple-Banking-System/blob/main/docs/images/DER_logico.JPG)


# Fluxo da Aplicação - Diagrama de sequencia(Em construção)
