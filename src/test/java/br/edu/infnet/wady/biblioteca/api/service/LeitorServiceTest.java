package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.repository.LeitorRepository;
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

class LeitorServiceTest {

    @Mock
    private LeitorRepository leitorRepository;

    @InjectMocks
    private LeitorService service;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

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
    @DisplayName("Criar deve salvar o leitor via repositório")
    void criarDeveSalvarLeitor() {
        Leitor leitor = novoLeitor("Ana Paula");
        when(leitorRepository.save(any(Leitor.class))).thenReturn(leitor);

        Leitor salvo = service.criar(leitor);

        assertNotNull(salvo);
        verify(leitorRepository, times(1)).save(leitor);
    }

    @Test
    @DisplayName("Buscar por ID existente deve retornar Optional preenchido")
    void buscarPorIdExistente() {
        Leitor leitor = novoLeitor("Carlos Eduardo");
        when(leitorRepository.findById(1L)).thenReturn(Optional.of(leitor));

        Optional<Leitor> encontrado = service.buscarPorId(1L);

        assertTrue(encontrado.isPresent());
        assertEquals("Carlos Eduardo", encontrado.get().getNome());
    }

    @Test
    @DisplayName("Buscar por ID inexistente deve retornar Optional vazio")
    void buscarPorIdInexistente() {
        when(leitorRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Leitor> resultado = service.buscarPorId(999L);

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Listar todos deve retornar todos os leitores")
    void listarTodos() {
        when(leitorRepository.findAll()).thenReturn(
                List.of(
                        novoLeitor("Leitor 1"),
                        novoLeitor("Leitor 2"),
                        novoLeitor("Leitor 3")
                )
        );

        List<Leitor> lista = service.listarTodos();

        assertEquals(3, lista.size());
        verify(leitorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Alterar deve atualizar dados quando o ID existir")
    void alterarExistente() {
        Leitor existente = novoLeitor("Antigo");
        existente.setId(1L);
        Leitor novo = novoLeitor("Novo");

        when(leitorRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(leitorRepository.save(any(Leitor.class))).thenReturn(existente);

        Leitor alterado = service.alterar(1L, novo);

        assertEquals("Novo", alterado.getNome());
        verify(leitorRepository).save(existente);
    }

    @Test
    @DisplayName("Alterar deve lançar exceção quando o ID não existir")
    void alterarNaoExistente() {
        when(leitorRepository.findById(999L)).thenReturn(Optional.empty());
        Leitor novo = novoLeitor("Qualquer");

        assertThrows(PessoaNaoEncontradaException.class, () -> service.alterar(999L, novo));
    }

    @Test
    @DisplayName("Excluir deve remover leitor existente")
    void excluir() {
        when(leitorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(leitorRepository).deleteById(1L);

        service.excluir(1L);

        verify(leitorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Excluir deve lançar exceção quando ID não existir")
    void excluirNaoExistente() {
        when(leitorRepository.existsById(999L)).thenReturn(false);

        assertThrows(PessoaNaoEncontradaException.class, () -> service.excluir(999L));
    }
}
