-- Categorias
INSERT INTO categoria (nome) VALUES
('Programação'),
('Front-end'),
('Back-end'),
('DevOps');

-- Alunos
INSERT INTO aluno (nome, email) VALUES
('Ana Silva', 'ana@email.com'),
('Carlos Souza', 'carlos@email.com'),
('Mariana Oliveira', 'mariana@email.com');

-- Cursos
INSERT INTO curso (titulo, descricao) VALUES
('Spring Boot Avançado', 'Domine Spring Boot com projetos reais'),
('React JS Fundamentos', 'Aprenda React do zero ao avançado'),
('Docker para Desenvolvedores', 'Containerização de aplicações');

-- Relacionamentos
INSERT INTO curso_categoria (curso_id, categoria_id) VALUES
(1, 1), (1, 3),  -- Spring Boot
(2, 1), (2, 2),  -- React
(3, 1), (3, 4);  -- Docker