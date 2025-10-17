package br.edu.infnet.wady.biblioteca.api.loader;

import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import br.edu.infnet.wady.biblioteca.api.model.Endereco;
import br.edu.infnet.wady.biblioteca.api.service.BibliotecarioService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Order(1)
public class BibliotecarioLoader implements ApplicationRunner {

    private final BibliotecarioService bibliotecarioService;

    public BibliotecarioLoader(BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\nIniciando carregamento de dados de bibliotecários...");

        try {
            ClassPathResource resource = new ClassPathResource("loader/bibliotecarios.txt");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String linha;
                int contador = 0;

                while ((linha = reader.readLine()) != null) {
                    if (linha.trim().isEmpty()) {
                        continue;
                    }

                    String[] dados = linha.split(";");

                    if (dados.length >= 8) {
                        Endereco endereco = new Endereco(
                                null, // cep
                                dados[6].trim(), // logradouro
                                dados[7].trim(), // numero
                                null, // complemento
                                null, // bairro
                                null, // cidade
                                null, // estado
                                null  // pais
                        );

                        Bibliotecario bibliotecario = new Bibliotecario(
                                dados[0].trim(), // nome
                                dados[1].trim(), // email
                                dados[2].trim(), // cpf
                                dados[3].trim(), // telefone
                                dados[4].trim(), // matricula
                                Double.valueOf(dados[5].trim()), // salario
                                endereco
                        );

                        bibliotecarioService.salvar(bibliotecario);
                        contador++;
                    }
                }

                System.out.println(contador + " bibliotecários carregados com sucesso!");
            }
        } catch (Exception e) {
            System.err.printf("Erro ao carregar dados de bibliotecários (%s): %s%n",
                    e.getClass().getSimpleName(), e.getMessage());
        }

        System.out.println("\nLista de bibliotecários carregados:");
        System.out.println("═════════════════════════════════════════════");

        List<Bibliotecario> todosBibliotecarios = bibliotecarioService.listarTodos();
        for (Bibliotecario bibliotecario : todosBibliotecarios) {
            System.out.println(bibliotecario);
        }

        System.out.println("═════════════════════════════════════════════");
    }
}
