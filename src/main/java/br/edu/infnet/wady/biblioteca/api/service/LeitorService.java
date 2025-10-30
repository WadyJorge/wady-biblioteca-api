package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.repository.LeitorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeitorService implements CrudService<Leitor, Long> {

    private LeitorRepository leitorRepository;

    public LeitorService(LeitorRepository leitorRepository) {
        this.leitorRepository = leitorRepository;
    }

    // Cria um novo leitor
    @Override
    public Leitor criar(Leitor leitor) {
        return leitorRepository.save(leitor);
    }

    // Lista todos os leitores
    @Override
    public List<Leitor> listarTodos() {
        return leitorRepository.findAll();
    }

    // Lista leitores ativos
    public List<Leitor> listarAtivos() {
        return leitorRepository.findByAtivoTrue();
    }

    // Lista leitores inativos
    public List<Leitor> listarInativos() {
        return leitorRepository.findByAtivoFalse();
    }

    // Busca um leitor pelo ID
    @Override
    public Optional<Leitor> buscarPorId(Long id) {
        return leitorRepository.findById(id);
    }

    // Busca leitores por nome
    public List<Leitor> buscarPorNome(String nome) {
        return leitorRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Busca leitor por CPF
    public Optional<Leitor> buscarPorCpf(String cpf) {
        return leitorRepository.findByCpf(cpf);
    }

    // Busca leitor por matrícula
    public Optional<Leitor> buscarPorMatricula(String matricula) {
        return leitorRepository.findByMatricula(matricula);
    }

    // Busca leitores por período de inscrição
    public List<Leitor> buscarPorPeriodoInscricao(LocalDate inicio, LocalDate fim) {
        return leitorRepository.findByDataInscricaoBetween(inicio, fim);
    }

    // Atualiza os dados de um leitor existente
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

    // Marca um leitor como inativo
    public Leitor inativar(Long id) {
        Leitor leitor = buscarPorId(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Leitor", id));

        leitor.setAtivo(false);
        return leitorRepository.save(leitor);
    }

    // Reativa um leitor inativo
    public Leitor reativar(Long id) {
        Leitor leitor = buscarPorId(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Leitor", id));
        leitor.setAtivo(true);
        return leitorRepository.save(leitor);
    }

    // Exclui um leitor pelo ID
    @Override
    public void excluir(Long id) {
        if (!leitorRepository.existsById(id)) {
            throw new PessoaNaoEncontradaException("Leitor", id);
        }
        leitorRepository.deleteById(id);
    }
}
