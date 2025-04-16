# 🏫 Edutech - Plataforma de Gestão de Cursos

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.4-brightgreen
)](https://spring.io/projects/spring-boot)
[![H2](https://img.shields.io/badge/H2-Database-yellowgreen)](https://www.h2database.com/)

Sistema de gerenciamentos de cursos online com relacionamentos ManyToMany (Alunos -- Cursos -- Categorias), desenvolvido com Spring Boot e H2.

## 📌 Funcionalidades

- **Matriculas**:
    - Alunos podem se inscrever em mútiplos cursos
    - Registro automático da data de inscrição

- **Cursos**:
    - Associação a múltiplas categorias
    - Busca por categoria/título

- **Dashboard**:
    - Listagem de alunos por curso
    - Cursos por categoria

## 🛠 Tecnologias

| Componente   | Tecnologia                     |
|--------------|--------------------------------|
| Backend      | Java 17, Spring Boot 3.4.4     |
| Persistência | Spring Data JPA, H2 Database   |
| Frontend     | Thymeleaf, Bootstrap 5         |
| Ferramentas  | Lombok, ModelMapper, Dev Tools |

## Como testar?

- Na classe config DataInitiliazer tem as credenciais de acesso de uma conta admin onde é possível testar todas as funções do site(criar cursos, editar alunos, etc);
- No momento uma conta nova como usuário só tem acesso a função nova matricula.