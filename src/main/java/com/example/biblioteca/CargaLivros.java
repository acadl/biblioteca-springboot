package com.example.biblioteca;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.repository.LivroRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CargaLivros implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CargaLivros.class);
    private static final int TAMANHO_LOTE = 1000;
    private static final int TAMANHO_MAX_TITULO = 255; // ajuste conforme a coluna "nome" no V1__Livro.sql

    private final LivroRepository repository;

    public CargaLivros(LivroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) {
            log.info("Banco já possui livros, carga ignorada");
            return;
        }

        CSVFormat formato = CSVFormat.DEFAULT.builder().setDelimiter(';').build();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("db/migration/books.csv").getInputStream(),
                StandardCharsets.ISO_8859_1))) {

            int idxTitulo = -1;
            CSVRecord cabecalho = parseLinha(reader.readLine(), formato);
            int qtdColunas = cabecalho.size();
            for (int i = 0; i < qtdColunas; i++) {
                if (cabecalho.get(i).trim().equalsIgnoreCase("Book-Title")) {
                    idxTitulo = i;
                    break;
                }
            }
            if (idxTitulo == -1) {
                throw new IllegalStateException("Coluna Book-Title não encontrada no cabeçalho");
            }

            List<Livro> lote = new ArrayList<>();
            int importados = 0, ignorados = 0;
            String linha;

            while ((linha = reader.readLine()) != null) {

                // 1) parse: só erros de leitura do CSV caem no catch
                String titulo;
                try {
                    CSVRecord registro = parseLinha(linha, formato);
                    if (registro.size() != qtdColunas) {
                        ignorados++;
                        continue;
                    }
                    titulo = registro.get(idxTitulo).trim();
                } catch (Exception e) {
                    ignorados++;
                    continue;
                }

                // 2) validação
                if (titulo.isEmpty() || titulo.length() > TAMANHO_MAX_TITULO) {
                    ignorados++;
                    continue;
                }

                // 3) gravação em lote: erro de banco NÃO é engolido
                lote.add(new Livro(titulo));
                if (lote.size() == TAMANHO_LOTE) {
                    repository.saveAll(lote);
                    importados += lote.size();
                    lote.clear();
                }
            }

            if (!lote.isEmpty()) {
                repository.saveAll(lote);
                importados += lote.size();
            }

            log.info("Carga concluída: {} livros importados, {} linhas ignoradas", importados, ignorados);
        }
    }

    private CSVRecord parseLinha(String linha, CSVFormat formato) throws IOException {
        try (CSVParser parser = CSVParser.parse(linha, formato)) {
            return parser.getRecords().get(0);
        }
    }
}