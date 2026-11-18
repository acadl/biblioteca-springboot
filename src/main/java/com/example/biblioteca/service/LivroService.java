package com.example.biblioteca.service;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.repository.LivroRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository repository){
        this.livroRepository = repository;
    }

    public List<Livro> buscarTodosLivros(){
        List<Livro> listarlivros = new ArrayList<>();
        listarlivros = this.livroRepository.findAll();
        return listarlivros;
    }

    public Livro editarLivro(UUID idLivro, String tituloLivro) {
        Livro livro = livroRepository.findById(idLivro).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livro.setNome(tituloLivro);

        return livro;
    }

    public String excluirLivro(UUID idLivro) {
        Livro livro = livroRepository.findById(idLivro).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livroRepository.delete(livro);

        return "Livro " + livro.getNome() + "excluído com sucesso!";
    }

    public Livro adicionarLivro(@NotBlank String tituloLivro) {
        Livro livro = new Livro();
        livro.setNome(tituloLivro);
        Livro livroSalvo = livroRepository.save(livro);

        return livroSalvo;
    }
}
