package br.edu.infnet.wady.biblioteca.api.loader;

import br.edu.infnet.wady.biblioteca.api.model.Livro;
import br.edu.infnet.wady.biblioteca.api.service.LivroService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class LivroLoader implements ApplicationRunner {

    private final LivroService livroService;

    public LivroLoader(LivroService livroService) {
        this.livroService = livroService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\nIniciando carregamento de dados de livros...");

        try {
            ClassPathResource resource = new ClassPathResource("livros.txt");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String linha;
                int contador = 0;
                while ((linha = reader.readLine()) != null) {

                    if (linha.trim().isEmpty()) continue;
                    String[] dados = linha.split(",");

                    if (dados.length >= 6) {
                        Livro livro = new Livro(
                                dados[0].trim(), // titulo
                                dados[1].trim(), // autor
                                dados[2].trim(), // editora
                                dados[3].trim(), // categoria
                                Integer.valueOf(dados[4].trim()), // anoPublicacao
                                Boolean.valueOf(dados[5].trim()) // disponivel
                        );
                        livroService.salvar(livro);
                        contador++;
                    }
                }
                System.out.println(contador + " livros carregados com sucesso!");
            }
        } catch (Exception e) {
            System.err.printf("Erro ao carregar dados de livros (%s): %s%n",
                    e.getClass().getSimpleName(), e.getMessage());
        }

        System.out.println("\nLista de livros carregados:");
        System.out.println("═════════════════════════════════════════════");
        List<Livro> todosLivros = livroService.listarTodos();
        for (Livro livro : todosLivros) {
            System.out.println(livro);
        }
        System.out.println("═════════════════════════════════════════════");
    }
}
