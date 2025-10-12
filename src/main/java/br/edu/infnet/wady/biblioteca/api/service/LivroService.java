package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.model.Livro;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LivroService implements CrudService<Livro, Long> {

    private final Map<Long, Livro> livrosMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Livro salvar(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(idGenerator.getAndIncrement());
        }
        livrosMap.put(livro.getId(), livro);
        return livro;
    }

    @Override
    public Optional<Livro> buscarPorId(Long id) {
        return Optional.ofNullable(livrosMap.get(id));
    }

    @Override
    public List<Livro> listarTodos() {
        return new ArrayList<>(livrosMap.values());
    }

    @Override
    public Livro alterar(Long id, Livro livro) {
        if (!livrosMap.containsKey(id)) {
            throw new NoSuchElementException("Livro com ID " + id + " não encontrado.");
        }
        livro.setId(id);
        livrosMap.put(id, livro);
        return livro;
    }

    @Override
    public void excluir(Long id) {
        livrosMap.remove(id);
    }
}
