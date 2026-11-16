-- Contexto: Livro
CREATE SCHEMA IF NOT EXISTS bilbioteca;

CREATE TABLE biblioteca.livros (
                                    id                UUID         PRIMARY KEY,
                                    nome              VARCHAR(150) NOT NULL

);