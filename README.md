
<div  align="center">

  

# 📋 Task Manager API


  

</div>

  

---

  

## 📋 Descrição do Projeto

  

O **Task Manager** é uma API RESTful construída para gerenciamento de tarefas internas, permitindo que usuários criem, atualizem e acompanhem suas tarefas e subtarefas de forma organizada e consistente.

  

### Problema que resolve

  

Equipes e indivíduos precisam de um sistema confiável para organizar fluxos de trabalho com suporte a hierarquia de tarefas. 
  

---

  

## 🚀 Tecnologias Utilizadas

  

-  **Java 17** 

-  **Spring Boot**

-  **Spring Data JPA** 

-  **Spring Validation**

-  **PostgreSQL 15**

-  **MapStruct** 

-  **Lombok**

-  **SpringDoc OpenAPI 3.0.2**

-  **Docker / Docker Compose**

-  **Maven 3.9.6** 

  

---

  

## 🏗️ Arquitetura e Decisões de Design

  

O projeto adota uma **arquitetura modular por domínio** (package-by-feature).

  

### Decisões de Design

  

-  **Package-by-feature**

-  **Records como DTOs**

-  **MapStruct** — Mapeamento entity↔DTO gerado em tempo de compilação, sem reflexão em runtime

-  **Interface `ControllerDocs`** — Desacoplamento das anotações Swagger do controller, mantendo o código limpo

-  **`GlobalExceptionHandler`** — Tratamento centralizado de erros com respostas padronizadas em JSON

-  **`@Transactional(readOnly = true)`**

-  **Paginação via `Pageable`**

-  **Dockerfile**

  

---

  

## ⚙️ Como Rodar o Projeto

  

### Pré-requisitos

  

- [Docker](https://www.docker.com/get-started) e [Docker Compose](https://docs.docker.com/compose/) instalados

  

### 🐳 Subindo com Docker Compose (recomendado)

  

O Docker Compose sobe automaticamente o banco de dados PostgreSQL e a API.

  

```bash

# 1. Clone o repositório

git  clone  https://github.com/lucasamscc/taskmanager.git

cd  taskmanager

  

# 2. Suba os containers

docker  compose  up  --build

  

# A API estará disponível em: http://localhost:8080

# O Swagger UI estará em: http://localhost:8080/swagger-ui.html

```

  

> **Nota:** O banco de dados roda na porta `5434` na sua máquina (mapeada para a `5432` interna do container).

  

### Variáveis de Ambiente

  

As variáveis já estão configuradas no `docker-compose.yml`. Para customizar:

  

-  `SPRING_DATASOURCE_URL` — URL de conexão com o banco (padrão: `jdbc:postgresql://db:5432/taskmanager`)

-  `SPRING_DATASOURCE_USERNAME` — Usuário do banco (padrão: `postgres`)

-  `SPRING_DATASOURCE_PASSWORD` — Senha do banco (padrão: `postgres`)

-  `SPRING_JPA_HIBERNATE_DDL_AUTO` — Estratégia DDL do Hibernate (padrão: `update`)

  

### ▶️ Rodando Localmente (sem Docker)

  

```bash

# Requer Java 17+ e Maven 3.9+, e um PostgreSQL local rodando

./mvnw  spring-boot:run

```

  

---

  

## 🔌 Endpoints Principais

  

### 👤 Usuários

  

-  **`POST /users`** — Cria um novo usuário

```json

// Request body

{ "name": "Lucas Martins", "email": "lucas@email.com" }

  

// Response 201

{ "id": "a1b2c3d4-...", "name": "Lucas Martins", "email": "lucas@email.com" }

```

  

-  **`GET /users/{id}`** — Busca um usuário por ID

  

### ✅ Tarefas

  

-  **`POST /tasks`** — Cria uma nova tarefa vinculada a um usuário

```json

// Request body

{ "title": "Implementar autenticação JWT", "description": "Opcional", "userId": "a1b2c3d4-..." }

  

// Response 201

{ "id": "f1e2d3c4-...", "title": "Implementar autenticação JWT", "status": "PENDING", "createdAt": "2026-04-29T19:00:00", "completedAt": null, "userId": "a1b2c3d4-..." }

```

  

-  **`GET /tasks`** — Lista tarefas paginadas com filtro opcional por status

```

GET /tasks?page=0&size=10

GET /tasks?status=PENDING

GET /tasks?status=IN_PROGRESS

GET /tasks?status=COMPLETED

```

  

-  **`GET /tasks/{id}`** — Busca uma tarefa por ID

  

-  **`PATCH /tasks/{id}/status`** — Atualiza o status de uma tarefa

```json

// Request body

{ "status": "COMPLETED" }

```

> ⚠️ Retorna `422 Unprocessable Content` se houver subtarefas com status diferente de `COMPLETED`

  

### 📌 Subtarefas

  

-  **`POST /tasks/{taskId}/subtasks`** — Cria uma subtarefa vinculada a uma tarefa

```json

// Request body

{ "title": "Configurar biblioteca JJWT", "description": "Opcional" }

  

// Response 201

{ "id": "11223344-...", "title": "Configurar biblioteca JJWT", "status": "PENDING", "createdAt": "2026-04-29T19:05:00", "completedAt": null, "taskId": "f1e2d3c4-..." }

```

  

-  **`GET /tasks/{taskId}/subtasks`** — Lista subtarefas de uma tarefa (paginado)

  

-  **`PATCH /subtasks/{id}/status`** — Atualiza o status de uma subtarefa

```json

// Request body

{ "status": "COMPLETED" }

```

  

### ❌ Respostas de Erro Padronizadas

  

Todos os erros seguem um contrato JSON consistente com os campos `timestamp`, `status`, `error`, `message`, `path` e `fieldErrors` (preenchido em erros de validação `400`).

  

---

  

## 🧪 Como Rodar os Testes

  

```bash

# Executar todos os testes

./mvnw  test

  

# Gerar relatório de cobertura (se configurado com JaCoCo)

./mvnw  verify

```

  

---

  

## 📚 Documentação — Swagger UI

  

Com a aplicação rodando, acesse a documentação interativa:

  

**➡️ [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

  

A documentação é gerada automaticamente pelo **SpringDoc OpenAPI 3.0** e permite:

  

- Visualizar todos os endpoints com descrições

- Testar requisições diretamente pelo browser

- Consultar os schemas de request e response

- Verificar os códigos HTTP de resposta possíveis

  

A estratégia de documentação utilizada separa as anotações `@Operation` e `@ApiResponse` em **interfaces dedicadas** (`*ControllerDocs.java`), mantendo os Controllers limpos.

  

---

  

## 🧠 Conceitos Aplicados

-  Java 17 + Spring Boot

-  Entidades `User`, `Task` e `Subtask` com UUID auto-gerado

-  Regras de negócio isoladas na camada de Service

-  Bean Validation com `@NotBlank`, `@Email`, `@NotNull` nos DTOs

-  Banco de dados relacional (PostgreSQL 15)

-  Testes automatizados unitários e de integração

-  `completedAt` preenchido/limpo automaticamente conforme o status

-  Bloqueio de conclusão de tarefa quando há subtarefas pendentes (`BusinessRuleException` + `422`)

-  **Docker / Docker Compose** — Dockerfile multi-stage + Compose

-  **Swagger/OpenAPI** — SpringDoc 3.0.2 com interfaces `ControllerDocs` dedicadas

-  **Paginação e filtros** — `Pageable` + filtro por `status` no `GET /tasks`

-  **Segurança básica** — Validação de entrada + contrato de erro padronizado

  

### Padrões e Boas Práticas

  

-  **DTO Pattern** — Objetos de transferência de dados separados das entidades JPA.

-  **Record DTOs** — Uso de `record` Java para DTOs imutáveis, reduzindo boilerplate.

-  **MapStruct** — Mapeamento entity↔DTO gerado em tempo de compilação, com type-safety.

-  **Clean Code** — Métodos com responsabilidade única, nomenclatura expressiva.

-  **Separation of Concerns** — Controller (HTTP), Service (negócio) e Repository (dados).

-  **Transações** — `@Transactional` com `readOnly = true` onde aplicável para otimizar queries de leitura

-  **Package-by-feature** — Organização por domínio ao invés de por tipo técnico.

-  **Interface para Swagger** — Anotações OpenAPI isoladas em interfaces `*ControllerDocs`.

-  **Builder Pattern** — Uso do `@Builder` do Lombok para construção de entidades.

-  **Global Exception Handler** — Tratamento centralizado com `@RestControllerAdvice`.

---

  

## 👨‍💻 Autor

  

**Lucas de Andrade Martins**

- GitHub: [@lucasamscc](https://github.com/lucasamscc/)
