package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import br.edu.infnet.wady.biblioteca.api.repository.BibliotecarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioService implements CrudService<Bibliotecario, Long> {

    private final BibliotecarioRepository bibliotecarioRepository;

    public BibliotecarioService(BibliotecarioRepository bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    // Cria um novo bibliotecário
    @Override
    public Bibliotecario criar(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    // Lista todos os bibliotecários com o endereço associado
    @Override
    public List<Bibliotecario> listarTodos() {
        return bibliotecarioRepository.findAllWithEndereco();
    }

    // Busca um bibliotecário pelo ID
    @Override
    public Optional<Bibliotecario> buscarPorId(Long id) {
        return bibliotecarioRepository.findById(id);
    }

    // Busca bibliotecário por nome (case-insensitive)
    public List<Bibliotecario> buscarPorNome(String nome) {
        return bibliotecarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Busca bibliotecário por CPF
    public Optional<Bibliotecario> buscarPorCpf(String cpf) {
        return bibliotecarioRepository.findByCpf(cpf);
    }

    // Busca bibliotecário por e-mail
    public Optional<Bibliotecario> buscarPorEmail(String email) {
        return bibliotecarioRepository.findByEmail(email);
    }

    // Busca bibliotecário por matrícula
    public Optional<Bibliotecario> buscarPorMatricula(String matricula) {
        return bibliotecarioRepository.findByMatricula(matricula);
    }

    // Busca bibliotecários por faixa salarial
    public List<Bibliotecario> buscarPorFaixaSalarial(BigDecimal salarioMin,
                                                      BigDecimal salarioMax) {
        return bibliotecarioRepository.findBySalarioBetween(salarioMin, salarioMax);
    }

    // Atualiza os dados de um bibliotecário existente
    @Override
    public Bibliotecario alterar(Long id, Bibliotecario bibliotecario) {
        Bibliotecario bibliotecarioExistente = buscarPorId(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Bibliotecário", id));

        bibliotecarioExistente.setNome(bibliotecario.getNome());
        bibliotecarioExistente.setEmail(bibliotecario.getEmail());
        bibliotecarioExistente.setCpf(bibliotecario.getCpf());
        bibliotecarioExistente.setTelefone(bibliotecario.getTelefone());
        bibliotecarioExistente.setMatricula(bibliotecario.getMatricula());
        bibliotecarioExistente.setSalario(bibliotecario.getSalario());
        bibliotecarioExistente.setEndereco(bibliotecario.getEndereco());

        return bibliotecarioRepository.save(bibliotecarioExistente);
    }

    // Exclui um bibliotecário pelo ID
    @Override
    public void excluir(Long id) {
        if (!bibliotecarioRepository.existsById(id)) {
            throw new PessoaNaoEncontradaException("Bibliotecário", id);
        }
        bibliotecarioRepository.deleteById(id);
    }
}
