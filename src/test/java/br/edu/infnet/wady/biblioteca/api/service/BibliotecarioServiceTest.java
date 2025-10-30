package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import br.edu.infnet.wady.biblioteca.api.model.Endereco;
import br.edu.infnet.wady.biblioteca.api.repository.BibliotecarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BibliotecarioServiceTest {

    @Mock
    private BibliotecarioRepository bibliotecarioRepository;

    @InjectMocks
    private BibliotecarioService service;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    private Endereco novoEndereco() {
        return new Endereco(
                "01310-100",
                "Rua Teste",
                "100",
                "Apto 10",
                "Centro",
                "São Paulo",
                "SP", "Brasil");
    }

    private Bibliotecario novoBibliotecario(String nome) {
        return new Bibliotecario(
                nome,
                "email@teste.com",
                "123.456.789-00",
                "(11) 99999-9999",
                "BIB001",
                new BigDecimal("3500.00"),
                novoEndereco());
    }

    @Test
    @DisplayName("Criar deve salvar o bibliotecário via repositório")
    void criarDeveSalvarBibliotecario() {
        Bibliotecario bibliotecario = novoBibliotecario("João Silva");
        when(bibliotecarioRepository.save(any())).thenReturn(bibliotecario);

        Bibliotecario salvo = service.criar(bibliotecario);

        assertNotNull(salvo);
        verify(bibliotecarioRepository).save(bibliotecario);
    }

    @Test
    @DisplayName("Buscar por ID existente deve retornar Optional preenchido")
    void buscarPorIdExistente() {
        Bibliotecario bibliotecario = novoBibliotecario("Maria Santos");
        when(bibliotecarioRepository.findById(1L)).thenReturn(Optional.of(bibliotecario));

        Optional<Bibliotecario> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Maria Santos", resultado.get().getNome());
    }

    @Test
    @DisplayName("Buscar por ID inexistente deve retornar Optional vazio")
    void buscarPorIdInexistente() {
        when(bibliotecarioRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<Bibliotecario> resultado = service.buscarPorId(999L);

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Listar todos deve retornar todos os bibliotecários")
    void listarTodos() {
        when(bibliotecarioRepository.findAllWithEndereco()).thenReturn(
                List.of(
                        novoBibliotecario("B1"),
                        novoBibliotecario("B2")
                )
        );

        List<Bibliotecario> lista = service.listarTodos();

        assertEquals(2, lista.size());
        verify(bibliotecarioRepository).findAllWithEndereco();
    }

    @Test
    @DisplayName("Alterar deve atualizar dados quando o ID existir")
    void alterarExistente() {
        Bibliotecario existente = novoBibliotecario("Antigo");
        existente.setId(1L);
        Bibliotecario novo = novoBibliotecario("Novo");

        when(bibliotecarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(bibliotecarioRepository.save(any())).thenReturn(existente);

        Bibliotecario alterado = service.alterar(1L, novo);

        assertEquals("Novo", alterado.getNome());
        verify(bibliotecarioRepository).save(existente);
    }

    @Test
    @DisplayName("Alterar deve lançar exceção quando o ID não existir")
    void alterarNaoExistente() {
        when(bibliotecarioRepository.findById(999L)).thenReturn(Optional.empty());
        Bibliotecario novo = novoBibliotecario("Qualquer");

        assertThrows(PessoaNaoEncontradaException.class, () -> service.alterar(999L, novo));
    }

    @Test
    @DisplayName("Excluir deve remover bibliotecário existente")
    void excluir() {
        when(bibliotecarioRepository.existsById(1L)).thenReturn(true);

        service.excluir(1L);

        verify(bibliotecarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Excluir deve lançar exceção quando o ID não existir")
    void excluirNaoExistente() {
        when(bibliotecarioRepository.existsById(999L)).thenReturn(false);

        assertThrows(PessoaNaoEncontradaException.class, () -> service.excluir(999L));
    }
}
