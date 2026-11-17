package com.example.biblioteca.controller;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService service ;

    public LivroController(LivroService service){
        this.service = service;
    }

    @GetMapping
    private List<Livro> buscarLivros(){
        System.out.println("=====>> Camada controller. Acessando o service...");

        return service.buscarTodosLivros();
    }
}
