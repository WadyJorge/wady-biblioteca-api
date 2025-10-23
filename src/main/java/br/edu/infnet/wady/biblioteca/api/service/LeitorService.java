package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.repository.LeitorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeitorService implements CrudService<Leitor, Long> {

    private LeitorRepository leitorRepository;

    public LeitorService(LeitorRepository leitorRepository) {
        this.leitorRepository = leitorRepository;
    }

    @Override
    public Leitor criar(Leitor leitor) {
        return leitorRepository.save(leitor);
    }

    @Override
    public Optional<Leitor> buscarPorId(Long id) {
        return leitorRepository.findById(id);
    }

    @Override
    public List<Leitor> listarTodos() {
        return leitorRepository.findAll();
    }

    @Override
    public Leitor alterar(Long id, Leitor leitor) {
        Leitor leitorExistente = buscarPorId(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Leitor", id));

        leitorExistente.setNome(leitor.getNome());
        leitorExistente.setEmail(leitor.getEmail());
        leitorExistente.setCpf(leitor.getCpf());
        leitorExistente.setTelefone(leitor.getTelefone());
        leitorExistente.setMatricula(leitor.getMatricula());
        leitorExistente.setDataInscricao(leitor.getDataInscricao());
        leitorExistente.setAtivo(leitor.getAtivo());

        return leitorRepository.save(leitorExistente);
    }

    public Leitor inativar(Long id) {
        Leitor leitor = buscarPorId(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Leitor", id));

        leitor.setAtivo(false);
        return leitorRepository.save(leitor);
    }

    @Override
    public void excluir(Long id) {
        if (!leitorRepository.existsById(id)) {
            throw new PessoaNaoEncontradaException("Leitor", id);
        }

        leitorRepository.deleteById(id);
    }
}
