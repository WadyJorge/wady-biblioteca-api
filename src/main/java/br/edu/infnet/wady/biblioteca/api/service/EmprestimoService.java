package br.edu.infnet.wady.biblioteca.api.service;

import br.edu.infnet.wady.biblioteca.api.exception.EmprestimoNaoEncontradoException;
import br.edu.infnet.wady.biblioteca.api.model.Emprestimo;
import br.edu.infnet.wady.biblioteca.api.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService implements CrudService<Emprestimo, Long> {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    // Cria um novo empréstimo no banco de dados
    @Override
    public Emprestimo criar(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    // Lista todos os empréstimos cadastrados
    @Override
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAllAtivosWithDetails();
    }

    // Lista todos os empréstimos que ainda estão ativos
    public List<Emprestimo> listarAtivos() {
        return emprestimoRepository.findByAtivoTrue();
    }

    // Lista empréstimos já devolvidos
    public List<Emprestimo> listarDevolvidos() {
        return emprestimoRepository.findByDataDevolucaoRealIsNotNull();
    }

    // Lista empréstimos ativos que estão atrasados
    public List<Emprestimo> listarAtrasados() {
        return emprestimoRepository
                .findByAtivoTrueAndDataDevolucaoPrevistaBeforeAndDataDevolucaoRealIsNull(
                        LocalDate.now()
                );
    }

    // Busca um empréstimo pelo ID
    @Override
    public Optional<Emprestimo> buscarPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    // Busca todos os empréstimos por nome de leitor
    public List<Emprestimo> buscarPorNome(String nome) {
        return emprestimoRepository.findByLeitorNomeContainingIgnoreCase(nome);
    }

    // Busca todos os empréstimos por título de livro
    public List<Emprestimo> buscarPorTitulo(String titulo) {
        return emprestimoRepository.findByLivroTituloContainingIgnoreCase(titulo);
    }

    // Atualiza os dados de um empréstimo existente
    @Override
    public Emprestimo alterar(Long id, Emprestimo emprestimo) {
        Emprestimo emprestimoExistente = buscarPorId(id)
                .orElseThrow(() -> new EmprestimoNaoEncontradoException(id));

        emprestimoExistente.setLeitor(emprestimo.getLeitor());
        emprestimoExistente.setLivro(emprestimo.getLivro());
        emprestimoExistente.setDataEmprestimo(emprestimo.getDataEmprestimo());
        emprestimoExistente.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
        emprestimoExistente.setDataDevolucaoReal(emprestimo.getDataDevolucaoReal());
        emprestimoExistente.setAtivo(emprestimo.getAtivo());

        return emprestimoRepository.save(emprestimoExistente);
    }

    // Marca um empréstimo como devolvido, atualizando a data e o status
    public Emprestimo devolver(Long id) {
        Emprestimo emprestimo = buscarPorId(id)
                .orElseThrow(() -> new EmprestimoNaoEncontradoException(id));

        emprestimo.setDataDevolucaoReal(LocalDate.now());
        emprestimo.setAtivo(false);

        return emprestimoRepository.save(emprestimo);
    }

    // Exclui um empréstimo pelo ID
    @Override
    public void excluir(Long id) {
        if (!emprestimoRepository.existsById(id)) {
            throw new EmprestimoNaoEncontradoException(id);
        }
        emprestimoRepository.deleteById(id);
    }
}
