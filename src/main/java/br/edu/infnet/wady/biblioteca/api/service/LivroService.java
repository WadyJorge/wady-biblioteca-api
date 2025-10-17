package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.LivroNaoEncontradoException;
import br.edu.infnet.wady.biblioteca.api.model.Livro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LivroService implements CrudService<Livro, Long> {

    private final Map<Long, Livro> livros = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Livro salvar(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(idGenerator.getAndIncrement());
        }

        livros.put(livro.getId(), livro);
        return livro;
    }

    @Override
    public Optional<Livro> buscarPorId(Long id) {
        if (!livros.containsKey(id)) {
            throw new LivroNaoEncontradoException(id);
        }

        return Optional.ofNullable(livros.get(id));
    }

    @Override
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros.values());
    }

    @Override
    public Livro alterar(Long id, Livro livro) {
        if (!livros.containsKey(id)) {
            throw new LivroNaoEncontradoException(id);
        }

        livro.setId(id);
        livros.put(id, livro);
        return livro;
    }

    @Override
    public void excluir(Long id) {
        if (livros.remove(id) == null) {
            throw new LivroNaoEncontradoException(id);
        }
    }
}
