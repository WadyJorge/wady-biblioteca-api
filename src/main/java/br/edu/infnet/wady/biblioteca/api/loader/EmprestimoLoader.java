package br.edu.infnet.wady.biblioteca.api.loader;

import br.edu.infnet.wady.biblioteca.api.model.Emprestimo;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.model.Livro;
import br.edu.infnet.wady.biblioteca.api.repository.LeitorRepository;
import br.edu.infnet.wady.biblioteca.api.repository.LivroRepository;
import br.edu.infnet.wady.biblioteca.api.service.EmprestimoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(4)
public class EmprestimoLoader implements ApplicationRunner {

    private final EmprestimoService emprestimoService;
    private final LeitorRepository leitorRepository;
    private final LivroRepository livroRepository;

    public EmprestimoLoader(
            EmprestimoService emprestimoService,
            LeitorRepository leitorRepository,
            LivroRepository livroRepository
    ) {
        this.emprestimoService = emprestimoService;
        this.leitorRepository = leitorRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\nIniciando carregamento de dados de empréstimos...");

        try {
            ClassPathResource resource = new ClassPathResource("loader/emprestimos.txt");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String linha;
                int contador = 0;

                while ((linha = reader.readLine()) != null) {
                    if (linha.trim().isEmpty()) {
                        continue;
                    }

                    String[] dados = linha.split(";");

                    if (dados.length >= 5) {
                        String cpfLeitor = dados[0].trim();
                        String tituloLivro = dados[1].trim();
                        LocalDate dataEmprestimo = LocalDate.parse(dados[2].trim());
                        LocalDate dataDevolucaoPrevista = LocalDate.parse(dados[3].trim());
                        Boolean ativo = Boolean.parseBoolean(dados[4].trim());

                        Leitor leitor = leitorRepository.findByCpf(cpfLeitor)
                                .orElseThrow(() -> new RuntimeException("Leitor não encontrado com CPF: " + cpfLeitor));

                        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCase(tituloLivro);
                        if (livros.isEmpty()) {
                            throw new RuntimeException("Livro não encontrado: " + tituloLivro);
                        }
                        Livro livro = livros.get(0);

                        Emprestimo emprestimo = new Emprestimo(leitor, livro, dataEmprestimo, dataDevolucaoPrevista);
                        emprestimo.setAtivo(ativo);

                        emprestimoService.criar(emprestimo);
                        contador++;
                    }
                }

                System.out.println(contador + " empréstimos carregados com sucesso!");
            }

        } catch (Exception e) {
            System.err.printf("Erro ao carregar dados de empréstimos (%s): %s%n",
                    e.getClass().getSimpleName(), e.getMessage());
        }

        System.out.println("\nLista completa de empréstimos:");
        System.out.println("══════════════════════════════════════════════════════════════════════════");

        List<Emprestimo> todosEmprestimos = emprestimoService.listarTodos();
        for (Emprestimo emprestimo : todosEmprestimos) {
            System.out.println(emprestimo);
        }
        System.out.println("══════════════════════════════════════════════════════════════════════════");
    }
}
