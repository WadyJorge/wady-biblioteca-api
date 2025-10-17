package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import br.edu.infnet.wady.biblioteca.api.model.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BibliotecarioServiceTest {

    private Endereco novoEndereco() {
        return new Endereco(
                "01310-100",
                "Rua Teste",
                "100",
                "Apto 10",
                "Centro",
                "São Paulo",
                "SP",
                "Brasil"
        );
    }

    private Bibliotecario novoBibliotecario(String nome) {
        return new Bibliotecario(
                nome,
                "email@teste.com",
                "123.456.789-00",
                "(11) 99999-9999",
                "BIB001",
                3500.00,
                novoEndereco()
        );
    }

    @Test
    @DisplayName("Criar deve gerar ID e persistir o bibliotecário em memória")
    void criarDeveGerarIdEGuardarBibliotecario() {
        BibliotecarioService service = new BibliotecarioService();
        Bibliotecario bibliotecario = novoBibliotecario("João Silva");

        Bibliotecario salvo = service.criar(bibliotecario);

        assertNotNull(salvo.getId(), "ID deve ser gerado ao salvar");

        List<Bibliotecario> todos = service.listarTodos();

        assertEquals(1, todos.size());
        assertEquals(salvo.getId(), todos.get(0).getId());
    }

    @Test
    @DisplayName("Buscar por ID existente deve retornar Optional preenchido")
    void buscarPorIdExistente() {
        BibliotecarioService service = new BibliotecarioService();
        Bibliotecario salvo = service.criar(novoBibliotecario("Maria Santos")); // CORREÇÃO: 'criar'

        Optional<Bibliotecario> encontrado = service.buscarPorId(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Maria Santos", encontrado.get().getNome());
    }

    @Test
    @DisplayName("Buscar por ID inexistente deve lançar exceção")
    void buscarPorIdInexistente() {
        BibliotecarioService service = new BibliotecarioService();

        assertThrows(PessoaNaoEncontradaException.class, () -> service.buscarPorId(999L));
    }

    @Test
    @DisplayName("Listar todos deve retornar todos os bibliotecários salvos")
    void listarTodos() {
        BibliotecarioService service = new BibliotecarioService();
        service.criar(novoBibliotecario("Bibliotecário 1")); // CORREÇÃO: 'criar'
        service.criar(novoBibliotecario("Bibliotecário 2")); // CORREÇÃO: 'criar'

        List<Bibliotecario> lista = service.listarTodos();

        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Alterar deve substituir os dados quando o ID existir")
    void alterarExistente() {
        BibliotecarioService service = new BibliotecarioService();
        Bibliotecario salvo = service.criar(novoBibliotecario("Nome Antigo")); // CORREÇÃO: 'criar'
        Long id = salvo.getId();

        Bibliotecario novo = novoBibliotecario("Nome Novo");
        Bibliotecario alterado = service.alterar(id, novo);

        assertEquals(id, alterado.getId(), "ID deve permanecer o mesmo");
        assertEquals("Nome Novo", alterado.getNome());
        assertEquals(1, service.listarTodos().size(), "Deve continuar existindo apenas um registro");
    }

    @Test
    @DisplayName("Alterar deve lançar exceção quando o ID não existir")
    void alterarNaoExistente() {
        BibliotecarioService service = new BibliotecarioService();
        Bibliotecario novo = novoBibliotecario("Qualquer");

        assertThrows(PessoaNaoEncontradaException.class, () -> service.alterar(999L, novo));
    }

    @Test
    @DisplayName("Excluir deve remover o bibliotecário e não encontrá-lo mais")
    void excluir() {
        BibliotecarioService service = new BibliotecarioService();
        Bibliotecario salvo = service.criar(novoBibliotecario("Para Excluir")); // CORREÇÃO: 'criar'
        Long id = salvo.getId();

        service.excluir(id);

        assertThrows(PessoaNaoEncontradaException.class, () -> service.buscarPorId(id));
        assertEquals(0, service.listarTodos().size());
    }

    @Test
    @DisplayName("Excluir deve lançar exceção quando o ID não existir")
    void excluirNaoExistente() {
        BibliotecarioService service = new BibliotecarioService();

        assertThrows(PessoaNaoEncontradaException.class, () -> service.excluir(999L));
    }
}
