# 🚀 Workshop Mongo

![Java](https://img.shields.io/badge/Java-21-red?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![MongoDB](https://img.shields.io/badge/MongoDB-Database-success?logo=mongodb)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?logo=docker)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-orange)

---

API desenvolvida para gerenciar **usuários**, **posts** e **comentários**, permitindo criar, ler, atualizar e excluir essas entidades.  
O objetivo é proporcionar o **primeiro contato com bancos de dados não relacionais**, com ênfase no **MongoDB**.

---

## 📑 Índice
- [Sobre](#-sobre)
- [Tecnologias](#-tecnologias)
- [Funcionalidades](#-funcionalidades)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como executar](#-como-executar)
- [Licença](#-licença)
- [Autor](#-autor)

---

## 🧩 Sobre

O **Workshop Mongo** é uma **API RESTful** desenvolvida para aplicar e consolidar conhecimentos em **Java** e **MongoDB**,  
simulando o funcionamento básico de uma **rede social** ou **sistema de blog**.

O projeto segue boas práticas de:
- Arquitetura em camadas (**Controller, Service, Repository**)
- Uso de **DTOs e mapeamento de entidades**
- Testes unitários com **JUnit/Mockito**
- Documentação automática via **Swagger/OpenAPI**

---

## ⚙️ Tecnologias

- **Java 21**
- **Spring Boot 3**
- **MongoDB**
- **JUnit / Mockito**
- **Docker**
- **Swagger / OpenAPI**

---

## 🚀 Funcionalidades

✅ Cadastro de usuários  
✅ Criação e listagem de postagens  
✅ Criação e listagem de comentários  
✅ Atualização e exclusão de entidades  
✅ Documentação automática com Swagger UI

---

## 🧠 Estrutura do Projeto

```bash
src/
 ├── main/java/com/marcelomelo/workshopmongo
 │    ├── controller/       # Endpoints REST
 │    ├── service/          # Regras de negócio
 │    ├── repository/       # Acesso ao MongoDB
 │    ├── domain/entities/  # Entidades de domínio
 │    ├── dtos/             # Data Transfer Objects
 │    └── exception/        # Exceções personalizadas
 └── resources/
      └── application.properties
```

---


## 🧱️ Como executar

🔹 Pré-requisitos

- Java 21+
- Maven
- MongoDB (local ou via Docker)

- Clone o repositório:

````
git clone https://github.com/SEU_USUARIO/workshop-mongo.git
````

- Entre na pasta do projeto:

````
cd workshop-mongo
````

- (Opcional) Suba o MongoDB com Docker:

````
docker run -d -p 28017:27017 --name mongo-db mongo
````

- Execute a aplicação:

````
./mvnw spring-boot:run
````

- Acesse a documentação no navegador:

````
http://localhost:8080/swagger-ui/index.html
````

---

## 📜 Licença

Este projeto está sob a licença MIT.
Você pode usar, modificar e distribuir livremente, desde que mantenha os créditos ao autor.