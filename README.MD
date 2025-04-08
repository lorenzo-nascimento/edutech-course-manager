# 🏫 Edutech - Plataforma de Gestão de Cursos

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
