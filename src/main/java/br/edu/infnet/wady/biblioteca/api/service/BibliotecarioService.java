package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import br.edu.infnet.wady.biblioteca.api.repository.BibliotecarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioService implements CrudService<Bibliotecario, Long> {

    private final BibliotecarioRepository bibliotecarioRepository;

    public BibliotecarioService(BibliotecarioRepository bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    @Override
    public Bibliotecario criar(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    @Override
    public Optional<Bibliotecario> buscarPorId(Long id) {
        return bibliotecarioRepository.findById(id);
    }

    @Override
    public List<Bibliotecario> listarTodos() {
        return bibliotecarioRepository.findAll();
    }

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

    @Override
    public void excluir(Long id) {
        if (!bibliotecarioRepository.existsById(id)) {
            throw new PessoaNaoEncontradaException("Bibliotecário", id);
        }

        bibliotecarioRepository.deleteById(id);
    }
}
