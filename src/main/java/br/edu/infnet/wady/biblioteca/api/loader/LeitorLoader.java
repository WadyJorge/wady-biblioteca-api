package br.edu.infnet.wady.biblioteca.api.loader;

import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.service.LeitorService;
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
@Order(2)
public class LeitorLoader implements ApplicationRunner {

    private final LeitorService leitorService;

    public LeitorLoader(LeitorService leitorService) {
        this.leitorService = leitorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\nIniciando carregamento de dados de leitores...");

        try {
            ClassPathResource resource = new ClassPathResource("loader/leitores.txt");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String linha;
                int contador = 0;

                while ((linha = reader.readLine()) != null) {
                    if (linha.trim().isEmpty()) {
                        continue;
                    }

                    String[] dados = linha.split(";");

                    if (dados.length >= 6) {
                        Leitor leitor = new Leitor(
                                dados[0].trim(), // nome
                                dados[1].trim(), // email
                                dados[2].trim(), // cpf
                                dados[3].trim(), // telefone
                                dados[4].trim(), // matricula
                                Boolean.valueOf(dados[5].trim()) // ativo
                        );

                        leitorService.criar(leitor);
                        contador++;
                    }
                }

                System.out.println(contador + " leitores carregados com sucesso!");
            }
        } catch (Exception e) {
            System.err.printf("Erro ao carregar dados de leitores (%s): %s%n",
                    e.getClass().getSimpleName(), e.getMessage());
        }

        System.out.println("\nLista completa de leitores:");
        System.out.println("═════════════════════════════════════════════");

        List<Leitor> todosLeitores = leitorService.listarTodos();
        for (Leitor leitor : todosLeitores) {
            System.out.println(leitor);
        }

        System.out.println("═════════════════════════════════════════════");
    }
}
