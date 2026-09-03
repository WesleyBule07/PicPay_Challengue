# PicPay Simplificado — Desafio Backend

## Sobre o projeto

Este projeto é uma implementação do desafio técnico de Backend do PicPay Simplificado, desenvolvida utilizando **Java e Spring Boot**.

A aplicação simula uma plataforma de pagamentos onde usuários podem realizar transferências de dinheiro entre si e para lojistas, respeitando as regras de negócio definidas no desafio.

O objetivo principal da implementação é demonstrar conhecimentos de desenvolvimento backend, arquitetura, REST APIs, persistência de dados, tratamento de erros, testes e boas práticas de desenvolvimento.

## Tecnologias

* **Java**
* **Spring Boot**
* **Spring Data JPA**
* **Spring Web**
* **Bean Validation**
* **PostgreSQL**
* **Docker / Docker Compose**
* **JUnit**
* **Mockito**
* **Maven**

## Arquitetura

A aplicação foi estruturada seguindo uma separação de responsabilidades entre as principais camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Controller

Responsável por receber as requisições HTTP, validar os dados de entrada e retornar as respostas adequadas.

### Service

Contém as regras de negócio da aplicação, incluindo:

* Validação do saldo do pagador;
* Validação do tipo de usuário;
* Autorização da transferência;
* Execução da transferência;
* Controle transacional;
* Comunicação com serviços externos.

### Repository

Responsável pela comunicação com o banco de dados através do Spring Data JPA.

### Domain / Entity

Representa as entidades e regras relacionadas ao domínio da aplicação, como usuários, carteiras e transferências.

## Regras de negócio

A aplicação considera os seguintes tipos de usuários:

* **Usuário comum** — pode enviar e receber dinheiro;
* **Lojista** — pode receber dinheiro, mas não pode realizar transferências.

Cada usuário possui uma carteira associada ao seu saldo.

Também são consideradas as seguintes regras:

* CPF/CNPJ deve ser único;
* E-mail deve ser único;
* O pagador precisa possuir saldo suficiente;
* Lojistas não podem realizar transferências;
* Uma transferência precisa ser autorizada por um serviço externo;
* A transferência deve ser executada de forma transacional;
* Em caso de falha, a operação deve ser revertida;
* O destinatário deve ser notificado após o recebimento do pagamento.

## Fluxo de transferência

O fluxo principal da aplicação pode ser representado da seguinte forma:

```text
Cliente
   │
   │ POST /transfer
   ▼
TransferController
   │
   ▼
TransferService
   │
   ├── Valida pagador
   ├── Valida destinatário
   ├── Valida saldo
   ├── Verifica autorização externa
   │
   ▼
Atualização das carteiras
   │
   ▼
Persistência da transferência
   │
   ▼
Serviço de notificação
   │
   ▼
Resposta HTTP
```

A operação de transferência é tratada como uma transação para garantir consistência entre o débito da carteira do pagador e o crédito da carteira do destinatário.

## Endpoint principal

### Realizar transferência

```http
POST /transfer
Content-Type: application/json
```

### Request

```json
{
  "value": 100.00,
  "payer": 4,
  "payee": 15
}
```

### Responsabilidades do endpoint

O endpoint é responsável por iniciar o processo de transferência. As validações e regras de negócio são delegadas à camada de serviço.

## Integrações externas

### Serviço de autorização

Antes da conclusão da transferência, a aplicação consulta um serviço externo para verificar se a operação está autorizada.

```http
GET https://util.devi.tools/api/v2/authorize
```

A integração é abstraída da regra de negócio para evitar um acoplamento direto entre o serviço de transferência e a implementação específica do cliente HTTP.

### Serviço de notificação

Após uma transferência bem-sucedida, a aplicação tenta enviar uma notificação ao destinatário através do serviço externo disponibilizado pelo desafio.

```http
POST https://util.devi.tools/api/v1/notify
```

Como serviços externos podem apresentar indisponibilidade ou instabilidade, o tratamento dessas falhas é considerado separadamente da operação principal.

## Tratamento de erros

A API possui tratamento centralizado de exceções para manter um padrão consistente nas respostas HTTP.

Exemplos de situações tratadas:

* Usuário não encontrado;
* Saldo insuficiente;
* Pagador sendo lojista;
* Destinatário inexistente;
* Transferência não autorizada;
* Dados de entrada inválidos;
* Falha em serviço externo;
* Erros inesperados da aplicação.

As respostas de erro seguem uma estrutura consistente para facilitar o consumo da API.

## Persistência

O banco de dados relacional é utilizado para armazenar os dados necessários para o funcionamento da aplicação.

As principais informações persistidas incluem:

* Usuários;
* Carteiras;
* Transferências.

A utilização de transações garante que alterações relacionadas a uma transferência não sejam parcialmente persistidas.

## Testes

Foram considerados testes para validar principalmente as regras de negócio da aplicação.

### Testes unitários

Utilizados para validar componentes isoladamente, especialmente a camada de serviço.

Exemplos:

* Transferência com saldo suficiente;
* Transferência sem saldo suficiente;
* Transferência realizada por lojista;
* Transferência para usuário inexistente;
* Transferência não autorizada.

### Testes de integração

Utilizados para validar a interação entre componentes da aplicação, incluindo controllers, serviços e persistência.

## Docker

A aplicação pode ser executada utilizando containers, reduzindo a necessidade de configuração manual do ambiente.

Exemplo:

```bash
docker compose up --build
```

## Como executar localmente

### Pré-requisitos

* Java instalado;
* Maven;
* Docker e Docker Compose.

### Executando com Maven

```bash
./mvnw spring-boot:run
```

### Executando os testes

```bash
./mvnw test
```

## Decisões arquiteturais

A solução prioriza:

* Separação de responsabilidades;
* Baixo acoplamento entre componentes;
* Facilidade de testes;
* Manutenibilidade;
* Clareza das regras de negócio;
* Tratamento explícito de erros;
* Uso de abstrações para integrações externas.

A aplicação foi mantida como um **monólito modular**, uma vez que o domínio do desafio é relativamente pequeno e não justifica a complexidade operacional de uma arquitetura baseada em múltiplos microsserviços.

## Possíveis melhorias

Caso o sistema evoluísse para um ambiente de produção, algumas melhorias poderiam ser consideradas:

* Implementação de autenticação e autorização;
* Idempotência para operações de transferência;
* Outbox Pattern para eventos e notificações;
* Mensageria utilizando RabbitMQ ou Kafka;
* Retry e Circuit Breaker para serviços externos;
* Cache para dados de leitura frequente;
* Observabilidade com métricas, logs estruturados e tracing;
* CI/CD;
* Maior cobertura de testes;
* Auditoria das movimentações financeiras;
* Controle de concorrência para evitar problemas em transferências simultâneas.

## Considerações finais

O projeto foi desenvolvido com foco não apenas no funcionamento do fluxo de transferência, mas também na organização, manutenibilidade e capacidade de evolução da aplicação.

As decisões técnicas foram tomadas considerando o tamanho atual do domínio e evitando adicionar complexidade arquitetural sem uma necessidade real.
