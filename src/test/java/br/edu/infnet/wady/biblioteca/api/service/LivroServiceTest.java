package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.LivroNaoEncontradoException;
import br.edu.infnet.wady.biblioteca.api.model.Livro;
import br.edu.infnet.wady.biblioteca.api.repository.LivroRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService service;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    private Livro novoLivro(String titulo) {
        return new Livro(
                titulo,
                "Autor Teste",
                "Editora Teste",
                "Categoria Teste",
                2024,
                true);
    }

    @Test
    @DisplayName("Criar deve salvar o livro via repositório")
    void criarDeveSalvarLivro() {
        Livro livro = novoLivro("Livro A");
        when(livroRepository.save(any())).thenReturn(livro);

        Livro salvo = service.criar(livro);

        assertNotNull(salvo);
        verify(livroRepository).save(livro);
    }

    @Test
    @DisplayName("Buscar por ID existente deve retornar Optional preenchido")
    void buscarPorIdExistente() {
        Livro livro = novoLivro("Livro B");
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        Optional<Livro> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Livro B", resultado.get().getTitulo());
    }

    @Test
    @DisplayName("Buscar por ID inexistente deve retornar Optional vazio")
    void buscarPorIdInexistente() {
        when(livroRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Livro> resultado = service.buscarPorId(999L);

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Listar todos deve retornar todos os livros")
    void listarTodos() {
        when(livroRepository.findAll()).thenReturn(List.of(novoLivro("L1"), novoLivro("L2")));

        List<Livro> lista = service.listarTodos();

        assertEquals(2, lista.size());
        verify(livroRepository).findAll();
    }

    @Test
    @DisplayName("Alterar deve atualizar dados quando o ID existir")
    void alterarExistente() {
        Livro existente = novoLivro("Antigo");
        existente.setId(1L);
        Livro novo = novoLivro("Novo");

        when(livroRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(livroRepository.save(any())).thenReturn(existente);

        Livro alterado = service.alterar(1L, novo);

        assertEquals("Novo", alterado.getTitulo());
        verify(livroRepository).save(existente);
    }

    @Test
    @DisplayName("Alterar deve lançar exceção quando o ID não existir")
    void alterarNaoExistente() {
        when(livroRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(LivroNaoEncontradoException.class, () -> service.alterar(999L, novoLivro("Qualquer")));
    }

    @Test
    @DisplayName("Excluir deve remover livro existente")
    void excluir() {
        when(livroRepository.existsById(1L)).thenReturn(true);

        service.excluir(1L);

        verify(livroRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Excluir deve lançar exceção quando o ID não existir")
    void excluirNaoExistente() {
        when(livroRepository.existsById(999L)).thenReturn(false);

        assertThrows(LivroNaoEncontradoException.class, () -> service.excluir(999L));
    }
}
