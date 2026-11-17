package com.example.biblioteca.service;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository repository){
        this.livroRepository = repository;
    }

    public List<Livro> buscarTodosLivros(){
        List<Livro> listarlivros = new ArrayList<>();
        System.out.println("=====>> Camada de Service. Aplicando as regras de negócio e acessando o repository!");
        listarlivros = this.livroRepository.findAll();

        return listarlivros;
    }
}
