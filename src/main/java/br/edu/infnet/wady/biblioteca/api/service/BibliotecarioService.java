package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BibliotecarioService implements CrudService<Bibliotecario, Long> {

    private final Map<Long, Bibliotecario> bibliotecarios = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Bibliotecario salvar(Bibliotecario bibliotecario) {
        if (bibliotecario.getId() == null) {
            bibliotecario.setId(idGenerator.getAndIncrement());
        }

        bibliotecarios.put(bibliotecario.getId(), bibliotecario);
        return bibliotecario;
    }

    @Override
    public Optional<Bibliotecario> buscarPorId(Long id) {
        if (!bibliotecarios.containsKey(id)) {
            throw new PessoaNaoEncontradaException("Bibliotecário", id);
        }

        return Optional.ofNullable(bibliotecarios.get(id));
    }

    @Override
    public List<Bibliotecario> listarTodos() {
        return new ArrayList<>(bibliotecarios.values());
    }

    @Override
    public Bibliotecario alterar(Long id, Bibliotecario bibliotecario) {
        if (!bibliotecarios.containsKey(id)) {
            throw new PessoaNaoEncontradaException("Bibliotecário", id);
        }

        bibliotecario.setId(id);
        bibliotecarios.put(id, bibliotecario);
        return bibliotecario;
    }

    @Override
    public void excluir(Long id) {
        if (bibliotecarios.remove(id) == null) {
            throw new PessoaNaoEncontradaException("Bibliotecário", id);
        }
    }
}
