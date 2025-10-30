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
import java.math.BigDecimal;
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

                    if (dados.length >= 14) {
                        Endereco endereco = new Endereco(
                                dados[6].trim(),  // cep
                                dados[7].trim(),  // logradouro
                                dados[8].trim(),  // numero
                                dados[9].trim(),  // complemento
                                dados[10].trim(), // bairro
                                dados[11].trim(), // cidade
                                dados[12].trim(), // estado
                                dados[13].trim()  // pais
                        );

                        Bibliotecario bibliotecario = new Bibliotecario(
                                dados[0].trim(), // nome
                                dados[1].trim(), // email
                                dados[2].trim(), // cpf
                                dados[3].trim(), // telefone
                                dados[4].trim(), // matricula
                                new BigDecimal(dados[5].trim()), // salario
                                endereco
                        );

                        bibliotecarioService.criar(bibliotecario);
                        contador++;
                    }
                }

                System.out.println(contador + " bibliotecários carregados com sucesso!");
            }
        } catch (Exception e) {
            System.err.printf("Erro ao carregar dados de bibliotecários (%s): %s%n",
                    e.getClass().getSimpleName(), e.getMessage());
        }

        System.out.println("\nLista completa de bibliotecários:");
        System.out.println("═════════════════════════════════════════════");

        List<Bibliotecario> todosBibliotecarios = bibliotecarioService.listarTodos();
        for (Bibliotecario bibliotecario : todosBibliotecarios) {
            System.out.println(bibliotecario);
        }

        System.out.println("═════════════════════════════════════════════");
    }
}
