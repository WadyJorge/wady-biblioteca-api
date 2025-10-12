package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.model.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LivroServiceTest {

    private Livro novoLivro(String titulo) {
        return new Livro(
                titulo,
                "Autor Teste",
                "Editora Teste",
                "Categoria Teste",
                2024,
                true
        );
    }

    @Test
    @DisplayName("Salvar deve gerar ID e persistir o livro em memória")
    void salvarDeveGerarIdEGuardarLivro() {
        LivroService service = new LivroService();
        Livro livro = novoLivro("Livro A");

        Livro salvo = service.salvar(livro);

        assertNotNull(salvo.getId(), "ID deve ser gerado ao salvar");
        List<Livro> todos = service.listarTodos();
        assertEquals(1, todos.size());
        assertEquals(salvo.getId(), todos.getFirst().getId());
    }

    @Test
    @DisplayName("Buscar por ID existente deve retornar Optional preenchido")
    void buscarPorIdExistente() {
        LivroService service = new LivroService();
        Livro salvo = service.salvar(novoLivro("Livro B"));

        Optional<Livro> encontrado = service.buscarPorId(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Livro B", encontrado.get().getTitulo());
    }

    @Test
    @DisplayName("Listar todos deve retornar todos os livros salvos")
    void listarTodos() {
        LivroService service = new LivroService();
        service.salvar(novoLivro("L1"));
        service.salvar(novoLivro("L2"));

        List<Livro> lista = service.listarTodos();

        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Alterar deve substituir os dados quando o ID existir")
    void alterarExistente() {
        LivroService service = new LivroService();
        Livro salvo = service.salvar(novoLivro("Antigo"));
        Long id = salvo.getId();

        Livro novo = novoLivro("Novo");
        Livro alterado = service.alterar(id, novo);

        assertEquals(id, alterado.getId(), "ID deve permanecer o mesmo");
        assertEquals("Novo", alterado.getTitulo());
        assertEquals(1, service.listarTodos().size(), "Deve continuar existindo apenas um registro");
    }

    @Test
    @DisplayName("Alterar deve lançar exceção quando o ID não existir")
    void alterarNaoExistente() {
        LivroService service = new LivroService();
        Livro novo = novoLivro("Qualquer");

        assertThrows(NoSuchElementException.class, () -> service.alterar(999L, novo));
    }

    @Test
    @DisplayName("Excluir deve remover o livro e não encontrá-lo mais")
    void excluir() {
        LivroService service = new LivroService();
        Livro salvo = service.salvar(novoLivro("Para excluir"));
        Long id = salvo.getId();

        service.excluir(id);

        assertTrue(service.buscarPorId(id).isEmpty());
        assertEquals(0, service.listarTodos().size());
    }
}
