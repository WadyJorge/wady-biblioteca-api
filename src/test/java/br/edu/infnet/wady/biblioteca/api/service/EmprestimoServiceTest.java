package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.EmprestimoNaoEncontradoException;
import br.edu.infnet.wady.biblioteca.api.model.Emprestimo;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.model.Livro;
import br.edu.infnet.wady.biblioteca.api.repository.EmprestimoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @InjectMocks
    private EmprestimoService service;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    private Leitor novoLeitor() {
        return new Leitor(
                "Leitor Teste",
                "leitor@teste.com",
                "123.456.789-00",
                "(11) 99999-9999",
                "LEIT001",
                true
        );
    }

    private Livro novoLivro() {
        return new Livro(
                "Livro Teste",
                "Autor Teste",
                "Editora Teste",
                "Categoria Teste",
                2024,
                true
        );
    }

    private Emprestimo novoEmprestimo() {
        return new Emprestimo(
                novoLeitor(),
                novoLivro(),
                LocalDate.now(),
                LocalDate.now().plusDays(14)
        );
    }

    @Test
    @DisplayName("Criar deve salvar o empréstimo via repositório")
    void criarDeveSalvarEmprestimo() {
        // Arrange
        Emprestimo emprestimo = novoEmprestimo();
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        // Act
        Emprestimo salvo = service.criar(emprestimo);

        // Assert
        assertNotNull(salvo);
        assertEquals(emprestimo, salvo);
        verify(emprestimoRepository, times(1)).save(emprestimo);
    }

    @Test
    @DisplayName("Buscar por ID existente deve retornar Optional preenchido")
    void buscarPorIdExistente() {
        // Arrange
        Emprestimo emprestimo = novoEmprestimo();
        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));

        // Act
        Optional<Emprestimo> resultado = service.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(emprestimo, resultado.get());
        verify(emprestimoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar por ID inexistente deve retornar Optional vazio")
    void buscarPorIdInexistente() {
        // Arrange
        when(emprestimoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Emprestimo> resultado = service.buscarPorId(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(emprestimoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Listar todos deve retornar todos os empréstimos ativos com detalhes")
    void listarTodos() {
        // Arrange
        List<Emprestimo> emprestimos = List.of(
                novoEmprestimo(),
                novoEmprestimo(),
                novoEmprestimo()
        );
        when(emprestimoRepository.findAllAtivosWithDetails()).thenReturn(emprestimos);

        // Act
        List<Emprestimo> lista = service.listarTodos();

        // Assert
        assertEquals(3, lista.size());
        assertFalse(lista.isEmpty());
        verify(emprestimoRepository, times(1)).findAllAtivosWithDetails();
    }

    @Test
    @DisplayName("Listar ativos deve retornar apenas empréstimos ativos")
    void listarAtivos() {
        // Arrange
        List<Emprestimo> emprestimos = List.of(novoEmprestimo(), novoEmprestimo());
        when(emprestimoRepository.findByAtivoTrue()).thenReturn(emprestimos);

        // Act
        List<Emprestimo> lista = service.listarAtivos();

        // Assert
        assertEquals(2, lista.size());
        verify(emprestimoRepository, times(1)).findByAtivoTrue();
    }

    @Test
    @DisplayName("Listar devolvidos deve retornar empréstimos com data de devolução")
    void listarDevolvidos() {
        // Arrange
        List<Emprestimo> emprestimos = List.of(novoEmprestimo());
        when(emprestimoRepository.findByDataDevolucaoRealIsNotNull()).thenReturn(emprestimos);

        // Act
        List<Emprestimo> lista = service.listarDevolvidos();

        // Assert
        assertEquals(1, lista.size());
        verify(emprestimoRepository, times(1)).findByDataDevolucaoRealIsNotNull();
    }

    @Test
    @DisplayName("Listar atrasados deve retornar empréstimos com devolução atrasada")
    void listarAtrasados() {
        // Arrange
        List<Emprestimo> emprestimos = List.of(novoEmprestimo());
        when(emprestimoRepository
                .findByAtivoTrueAndDataDevolucaoPrevistaBeforeAndDataDevolucaoRealIsNull(any(LocalDate.class)))
                .thenReturn(emprestimos);

        // Act
        List<Emprestimo> lista = service.listarAtrasados();

        // Assert
        assertEquals(1, lista.size());
        verify(emprestimoRepository, times(1))
                .findByAtivoTrueAndDataDevolucaoPrevistaBeforeAndDataDevolucaoRealIsNull(any(LocalDate.class));
    }

    @Test
    @DisplayName("Buscar por nome de leitor deve retornar empréstimos correspondentes")
    void buscarPorNome() {
        // Arrange
        List<Emprestimo> emprestimos = List.of(novoEmprestimo());
        when(emprestimoRepository.findByLeitorNomeContainingIgnoreCase("Leitor"))
                .thenReturn(emprestimos);

        // Act
        List<Emprestimo> lista = service.buscarPorNome("Leitor");

        // Assert
        assertEquals(1, lista.size());
        verify(emprestimoRepository, times(1))
                .findByLeitorNomeContainingIgnoreCase("Leitor");
    }

    @Test
    @DisplayName("Buscar por título de livro deve retornar empréstimos correspondentes")
    void buscarPorTitulo() {
        // Arrange
        List<Emprestimo> emprestimos = List.of(novoEmprestimo());
        when(emprestimoRepository.findByLivroTituloContainingIgnoreCase("Livro"))
                .thenReturn(emprestimos);

        // Act
        List<Emprestimo> lista = service.buscarPorTitulo("Livro");

        // Assert
        assertEquals(1, lista.size());
        verify(emprestimoRepository, times(1))
                .findByLivroTituloContainingIgnoreCase("Livro");
    }

    @Test
    @DisplayName("Alterar deve atualizar dados quando o ID existir")
    void alterarExistente() {
        // Arrange
        Emprestimo existente = novoEmprestimo();
        existente.setId(1L);
        Emprestimo novo = novoEmprestimo();
        novo.setDataEmprestimo(LocalDate.now().minusDays(5));

        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(existente);

        // Act
        Emprestimo alterado = service.alterar(1L, novo);

        // Assert
        assertNotNull(alterado);
        assertEquals(novo.getDataEmprestimo(), alterado.getDataEmprestimo());
        verify(emprestimoRepository, times(1)).findById(1L);
        verify(emprestimoRepository, times(1)).save(existente);
    }

    @Test
    @DisplayName("Alterar deve lançar exceção quando o ID não existir")
    void alterarNaoExistente() {
        // Arrange
        when(emprestimoRepository.findById(999L)).thenReturn(Optional.empty());
        Emprestimo novo = novoEmprestimo();

        // Act & Assert
        assertThrows(EmprestimoNaoEncontradoException.class,
                () -> service.alterar(999L, novo));
        verify(emprestimoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Devolver deve atualizar data de devolução e marcar como inativo")
    void devolver() {
        // Arrange
        Emprestimo emprestimo = novoEmprestimo();
        emprestimo.setId(1L);
        emprestimo.setAtivo(true);

        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        // Act
        Emprestimo devolvido = service.devolver(1L);

        // Assert
        assertNotNull(devolvido.getDataDevolucaoReal());
        assertFalse(devolvido.getAtivo());
        verify(emprestimoRepository, times(1)).findById(1L);
        verify(emprestimoRepository, times(1)).save(emprestimo);
    }

    @Test
    @DisplayName("Devolver deve lançar exceção quando o ID não existir")
    void devolverNaoExistente() {
        // Arrange
        when(emprestimoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmprestimoNaoEncontradoException.class,
                () -> service.devolver(999L));
        verify(emprestimoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Excluir deve remover empréstimo existente")
    void excluir() {
        // Arrange
        when(emprestimoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(emprestimoRepository).deleteById(1L);

        // Act
        service.excluir(1L);

        // Assert
        verify(emprestimoRepository, times(1)).existsById(1L);
        verify(emprestimoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Excluir deve lançar exceção quando o ID não existir")
    void excluirNaoExistente() {
        // Arrange
        when(emprestimoRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(EmprestimoNaoEncontradoException.class,
                () -> service.excluir(999L));
        verify(emprestimoRepository, times(1)).existsById(999L);
    }
}
