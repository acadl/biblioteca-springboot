package com.example.biblioteca.controller;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService service ;

    public LivroController(LivroService service){
        this.service = service;
    }

    @GetMapping
    private List<Livro> buscarTodosLivros(){
        return service.buscarTodosLivros();
    }

    @PatchMapping
    private Livro editarLivro(
            @RequestParam @NotNull UUID idLivro,
            @RequestParam @NotBlank String tituloLivro){
        return service.editarLivro(idLivro, tituloLivro);
    }

    @DeleteMapping
    private String excluirLivro(@RequestParam @NotNull UUID idLivro){
        return service.excluirLivro(idLivro);
    }

    @PostMapping
    private Livro criarLivro(@RequestParam @NotBlank String tituloLivro){
        return service.adicionarLivro(tituloLivro);
    }


}
