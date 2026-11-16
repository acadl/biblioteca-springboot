package com.example.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name="livros", schema="bilbioteca")
public class Livro {

    @Id
    private UUID id;
    private String nome;
}
