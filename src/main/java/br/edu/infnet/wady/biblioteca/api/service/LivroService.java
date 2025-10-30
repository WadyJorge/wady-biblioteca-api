package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.LivroNaoEncontradoException;
import br.edu.infnet.wady.biblioteca.api.model.Livro;
import br.edu.infnet.wady.biblioteca.api.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService implements CrudService<Livro, Long> {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    // Cria um novo livro
    @Override
    public Livro criar(Livro livro) {
        return livroRepository.save(livro);
    }

    // Lista todos os livros
    @Override
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    // Lista livros disponíveis
    public List<Livro> listarDisponiveis() {
        return livroRepository.findByDisponivelTrue();
    }

    // Lista livros indisponíveis
    public List<Livro> listarIndisponiveis() {
        return livroRepository.findByDisponivelFalse();
    }

    // Busca um livro pelo ID
    @Override
    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    // Busca livros por título
    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Busca livros por autor
    public List<Livro> buscarPorAutor(String autor) {
        return livroRepository.findByAutorContainingIgnoreCase(autor);
    }

    // Busca livros por categoria
    public List<Livro> buscarPorCategoria(String categoria) {
        return livroRepository.findByCategoriaIgnoreCase(categoria);
    }

    // Lista livros por intervalo de ano de publicação
    public List<Livro> buscarPorPeriodoPublicacao(Integer anoInicio,
                                                  Integer anoFim) {
        return livroRepository.findByAnoPublicacaoBetween(anoInicio, anoFim);
    }

    // Atualiza os dados de um livro existente
    @Override
    public Livro alterar(Long id, Livro livro) {
        Livro livroExistente = buscarPorId(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));

        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAutor(livro.getAutor());
        livroExistente.setEditora(livro.getEditora());
        livroExistente.setCategoria(livro.getCategoria());
        livroExistente.setAnoPublicacao(livro.getAnoPublicacao());
        livroExistente.setDisponivel(livro.getDisponivel());

        return livroRepository.save(livroExistente);
    }

    // Marca um livro como disponível
    public Livro marcarDisponivel(Long id) {
        Livro livro = buscarPorId(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
        livro.setDisponivel(true);
        return livroRepository.save(livro);
    }

    // Marca um livro como indisponível
    public Livro marcarIndisponivel(Long id) {
        Livro livro = buscarPorId(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
        livro.setDisponivel(false);
        return livroRepository.save(livro);
    }

    // Exclui um livro pelo ID
    @Override
    public void excluir(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroNaoEncontradoException(id);
        }
        livroRepository.deleteById(id);
    }
}
