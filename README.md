# 📚 Biblioteca API

![Status](https://img.shields.io/badge/status-em%20andamento-orange)
![Java](https://img.shields.io/badge/Java-21%2B-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-green)

API REST desenvolvida como projeto da disciplina **Arquitetura Java [25E4_2]**, parte da **Pós-Graduação MIT em Arquitetura de Software** do **Instituto Infnet**.

O objetivo é construir uma API para o gerenciamento de uma biblioteca, aplicando de forma prática os conceitos de **Programação Orientada a Objetos (POO)**, **arquitetura em camadas** e as melhores práticas do ecossistema **Spring Boot**.

## 📑 Índice

1. [Features Implementadas](#-features-implementadas)
2. [Arquitetura Aplicada](#-arquitetura-aplicada)
3. [Como Executar o Projeto](#-como-executar-o-projeto)
4. [Endpoints da API](#-endpoints-da-api)
5. [Autor](#-autor)

## ✨ Features Implementadas

O projeto está sendo construído de forma incremental:

* [x] **Feature 1:** Configuração base do Spring Boot, entidade `Livro`, persistência em memória e `Loader` a partir de arquivo `.txt`.
* [x] **Feature 2:** Expansão do domínio com **Herança** (`Pessoa`, `Leitor`, `Bibliotecario`) e **Associação** (`Endereco`). Implementação do **CRUD completo** (GET, POST, PUT, DELETE), método `PATCH` e tratamento de exceções customizadas.
* [ ] **Feature 3:** Persistência de dados com banco de dados relacional (Spring Data JPA).
* [ ] **Feature 4:** Validação de dados (Bean Validation), tratamento global de exceções, relacionamentos complexos (One-to-Many) e Query Methods.

## 🧩 Arquitetura Aplicada

O projeto segue o padrão **arquitetura em camadas**, visando **separação de responsabilidades** e **baixo acoplamento**:

```
biblioteca-api
├── controller/      → Exposição de endpoints REST
├── exception/       → Classes de exceções customizadas
├── loader           → Classes responsáveis pela carga inicial de dados
├── model/           → Entidades de domínio (Livro, Leitor, Bibliotecario, etc.)
└── service/         → Regras de negócio e validações       
```

## ⚙️ Como Executar o Projeto

### 📋 Pré-requisitos

* JDK **21+**
* **Apache Maven** 
* **Git** (ou baixe o ZIP do repositório)

### 🚀 Passos para execução

1. **Clone o repositório**

   ```bash
   git clone https://github.com/wadyjorge/wady-biblioteca-api.git
   ```

2. **Acesse o diretório do projeto**

   ```bash
   cd wady-biblioteca-api
   ```

3. **Execute a aplicação**

   ```bash
   ./mvnw spring-boot:run
   ```

   *(O comando `./mvnw` utiliza o Maven Wrapper, dispensando a instalação local do Maven.)*

4. **Acesse a API**

   ```
   http://localhost:8080
   ```

   Os dados iniciais serão carregados automaticamente pelos *Loaders*.
   Utilize **Postman** ou **Insomnia** para testar os endpoints abaixo.

## 🌐 Endpoints da API

### 📖 Livros

| Método   | Endpoint       | Descrição                    |
| :------- | :------------- | :--------------------------- |
| `GET`    | `/livros`      | Lista todos os livros.       |
| `GET`    | `/livros/{id}` | Busca um livro por ID.       |
| `POST`   | `/livros`      | Cadastra um novo livro.      |
| `PUT`    | `/livros/{id}` | Atualiza um livro existente. |
| `DELETE` | `/livros/{id}` | Exclui um livro por ID.      |

### 👩‍🏫 Bibliotecários

| Método   | Endpoint               | Descrição                            |
| :------- | :--------------------- | :----------------------------------- |
| `GET`    | `/bibliotecarios`      | Lista todos os bibliotecários.       |
| `GET`    | `/bibliotecarios/{id}` | Busca um bibliotecário por ID.       |
| `POST`   | `/bibliotecarios`      | Cadastra um novo bibliotecário.      |
| `PUT`    | `/bibliotecarios/{id}` | Atualiza um bibliotecário existente. |
| `DELETE` | `/bibliotecarios/{id}` | Exclui um bibliotecário por ID.      |

### 🧑‍💼 Leitores

| Método   | Endpoint                  | Descrição                                 |
| :------- | :------------------------ | :---------------------------------------- |
| `GET`    | `/leitores`               | Lista todos os leitores.                  |
| `GET`    | `/leitores/{id}`          | Busca um leitor por ID.                   |
| `POST`   | `/leitores`               | Cadastra um novo leitor.                  |
| `PUT`    | `/leitores/{id}`          | Atualiza um leitor existente.             |
| `PATCH`  | `/leitores/{id}/inativar` | Inativa um leitor (define `ativo=false`). |
| `DELETE` | `/leitores/{id}`          | Exclui um leitor por ID.                  |

## 👨‍💻 Autor

Para suporte ou feedback: 

**Wady Jorge**

📧 **E-mail:** [wbeliche@live.com](mailto:wbeliche@live.com) 
🔗 **LinkedIn:** [linkedin.com/in/wadyjorge](https://linkedin.com/in/wadyjorge)
