package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LeitorServiceTest {

    private Leitor novoLeitor(String nome) {
        return new Leitor(
                nome,
                "email@teste.com",
                "123.456.789-00",
                "(11) 99999-9999",
                "LEIT001",
                true
        );
    }

    @Test
    @DisplayName("Criar deve gerar ID e persistir o leitor em memória")
    void criarDeveGerarIdEGuardarLeitor() {
        LeitorService service = new LeitorService();
        Leitor leitor = novoLeitor("Ana Paula");

        Leitor salvo = service.criar(leitor); // CORREÇÃO: 'criar'

        assertNotNull(salvo.getId(), "ID deve ser gerado ao salvar");
        assertNotNull(salvo.getDataInscricao(), "Data de inscrição deve ser gerada");
        assertTrue(salvo.getAtivo(), "Leitor deve ser criado como ativo");

        List<Leitor> todos = service.listarTodos();

        assertEquals(1, todos.size());
        assertEquals(salvo.getId(), todos.get(0).getId());
    }

    @Test
    @DisplayName("Buscar por ID existente deve retornar Optional preenchido")
    void buscarPorIdExistente() {
        LeitorService service = new LeitorService();
        Leitor salvo = service.criar(novoLeitor("Carlos Eduardo")); // CORREÇÃO: 'criar'

        Optional<Leitor> encontrado = service.buscarPorId(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Carlos Eduardo", encontrado.get().getNome());
    }

    @Test
    @DisplayName("Buscar por ID inexistente deve lançar exceção")
    void buscarPorIdInexistente() {
        LeitorService service = new LeitorService();

        assertThrows(PessoaNaoEncontradaException.class, () -> service.buscarPorId(999L));
    }

    @Test
    @DisplayName("Listar todos deve retornar todos os leitores salvos")
    void listarTodos() {
        LeitorService service = new LeitorService();
        service.criar(novoLeitor("Leitor 1"));
        service.criar(novoLeitor("Leitor 2"));
        service.criar(novoLeitor("Leitor 3"));

        List<Leitor> lista = service.listarTodos();

        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Alterar deve substituir os dados quando o ID existir")
    void alterarExistente() {
        LeitorService service = new LeitorService();
        Leitor salvo = service.criar(novoLeitor("Nome Antigo"));
        Long id = salvo.getId();

        Leitor novo = novoLeitor("Nome Novo");
        Leitor alterado = service.alterar(id, novo);

        assertEquals(id, alterado.getId(), "ID deve permanecer o mesmo");
        assertEquals("Nome Novo", alterado.getNome());
        assertNotNull(alterado.getDataInscricao(), "Data de inscrição não deve ser nula");
        assertEquals(1, service.listarTodos().size(), "Deve continuar existindo apenas um registro");
    }

    @Test
    @DisplayName("Alterar deve lançar exceção quando o ID não existir")
    void alterarNaoExistente() {
        LeitorService service = new LeitorService();
        Leitor novo = novoLeitor("Qualquer");

        assertThrows(PessoaNaoEncontradaException.class, () -> service.alterar(999L, novo));
    }

    @Test
    @DisplayName("Inativar deve marcar o leitor como inativo")
    void inativarLeitor() {
        LeitorService service = new LeitorService();
        Leitor salvo = service.criar(novoLeitor("Pedro Costa"));
        Long id = salvo.getId();

        assertTrue(salvo.getAtivo(), "Leitor deve iniciar como ativo");

        Leitor inativado = service.inativar(id);

        assertFalse(inativado.getAtivo(), "Leitor deve estar inativo após inativar");
        assertEquals(id, inativado.getId());

        Optional<Leitor> consultado = service.buscarPorId(id);

        assertTrue(consultado.isPresent());
        assertFalse(consultado.get().getAtivo(), "Status inativo deve persistir");
    }

    @Test
    @DisplayName("Inativar deve lançar exceção quando o ID não existir")
    void inativarNaoExistente() {
        LeitorService service = new LeitorService();

        assertThrows(PessoaNaoEncontradaException.class, () -> service.inativar(999L));
    }

    @Test
    @DisplayName("Excluir deve remover o leitor e não encontrá-lo mais")
    void excluir() {
        LeitorService service = new LeitorService();
        Leitor salvo = service.criar(novoLeitor("Para Excluir"));
        Long id = salvo.getId();

        service.excluir(id);

        assertThrows(PessoaNaoEncontradaException.class, () -> service.buscarPorId(id));
        assertEquals(0, service.listarTodos().size());
    }

    @Test
    @DisplayName("Excluir deve lançar exceção quando o ID não existir")
    void excluirNaoExistente() {
        LeitorService service = new LeitorService();

        assertThrows(PessoaNaoEncontradaException.class, () -> service.excluir(999L));
    }


}
