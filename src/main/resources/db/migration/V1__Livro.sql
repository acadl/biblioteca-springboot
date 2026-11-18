-- Contexto: Livro
CREATE SCHEMA IF NOT EXISTS biblioteca;

CREATE TABLE biblioteca.livros (
                                    id                UUID         PRIMARY KEY,
                                    nome              VARCHAR(500) NOT NULL

);