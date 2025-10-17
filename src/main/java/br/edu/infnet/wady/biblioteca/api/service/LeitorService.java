package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LeitorService implements CrudService<Leitor, Long> {

    private final Map<Long, Leitor> leitores = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Leitor criar(Leitor leitor) {
        if (leitor.getId() == null) {
            leitor.setId(idGenerator.getAndIncrement());
        }

        leitores.put(leitor.getId(), leitor);
        return leitor;
    }

    @Override
    public Optional<Leitor> buscarPorId(Long id) {
        if (!leitores.containsKey(id)) {
            throw new PessoaNaoEncontradaException("Leitor", id);
        }

        return Optional.ofNullable(leitores.get(id));
    }

    @Override
    public List<Leitor> listarTodos() {
        return new ArrayList<>(leitores.values());
    }

    @Override
    public Leitor alterar(Long id, Leitor leitor) {
        if (!leitores.containsKey(id)) {
            throw new PessoaNaoEncontradaException("Leitor", id);
        }

        leitor.setId(id);
        leitores.put(id, leitor);

        return leitor;
    }

    public Leitor inativar(Long id) {
        Leitor leitor = buscarPorId(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Leitor", id));

        leitor.setAtivo(false);
        leitores.put(id, leitor);

        return leitor;
    }

    @Override
    public void excluir(Long id) {
        if (leitores.remove(id) == null) {
            throw new PessoaNaoEncontradaException("Leitor", id);
        }
    }
}
