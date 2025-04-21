# API de Produto
Este projeto é uma API Rest de produtos, com funcionalidades de CRUD (Create, Read, Update e Delete) e listagem de produtos.
API desenvolvida com Java e Spring Boot.

## Variáveis de Ambiente
Para executar o projeto, é necessário configurar as seguintes variáveis de ambiente:
- `Java`: JDK 17.
- `Gradle`: 3.4.4 ou superior.
- `PostgreSQL`: 15.4 ou superior.
- `Docker`: Opcional - para rodar em um container.
- `Git`: Para clonar o projeto.

## Configurar o Banco de Dados
1. Crie um banco de dados no PostgreSQL, por exemplo: `produtos`.
```cmd
psql -U username
password
CREATE DATABASE produtos;
```

2. Altere o arquivo `src/main/resources/application.yml` com as informações do seu banco de dados:

```properties
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/produtos
    username: postgres -- exemplo de username
    password: root  -- exemplo de senha
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

# Documentação da API

```http
Post /api/produto - Cria um novo produto.
``` 

| Parametros | Tipo | Descrição |
|------------|------|-----------|
| `id`       | UUID | ID do produto. |
| `nome`     | String | Nome do produto. |
| `descricao`| String | Descrição do produto. |
| `preco`    | Double | Preço do produto. |
| `quantidadeEstoque`| Integer | Quantidade do produto. |
| `dataCriacao`| OffsetDateTime | Data de criação do produto. |

```
Get /api/produto - Lista todos os produtos.
```
```
Get /api/produto/{id} - Busca um produto pelo ID.
```
```
Put /api/produto/{id} - Atualiza um produto pelo ID.
```
```
Delete /api/produto/{id} - Deleta um produto pelo ID.
```