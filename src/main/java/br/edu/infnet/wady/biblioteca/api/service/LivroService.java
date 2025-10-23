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

    @Override
    public Livro criar(Livro livro) {
        return livroRepository.save(livro);
    }

    @Override
    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    @Override
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

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

    @Override
    public void excluir(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroNaoEncontradoException(id);
        }

        livroRepository.deleteById(id);
    }
}
