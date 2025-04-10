-- Categorias
MERGE INTO categoria (id, nome) KEY (id) VALUES
(1, 'Programação'),
(2, 'Front-end'),
(3, 'Back-end'),
(4, 'DevOps');

-- Alunos (REMOVA os IDs explícitos)
INSERT INTO aluno (nome, email) VALUES
('Ana Silva', 'ana@email.com'),
('Carlos Souza', 'carlos@email.com'),
('Mariana Oliveira', 'mariana@email.com');

-- Cursos (REMOVA os IDs explícitos)
INSERT INTO curso (titulo, descricao) VALUES
('Spring Boot Avançado', 'Domine Spring Boot com projetos reais'),
('React JS Fundamentos', 'Aprenda React do zero ao avançado'),
('Docker para Desenvolvedores', 'Containerização de aplicações');


-- Relacionamentos
MERGE INTO curso_categoria (curso_id, categoria_id) KEY (curso_id, categoria_id) VALUES
(1, 1), (1, 3),
(2, 1), (2, 2),
(3, 1), (3, 4);