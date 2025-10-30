package br.edu.infnet.wady.biblioteca.api.repository;

import br.edu.infnet.wady.biblioteca.api.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByAtivoTrue();
    List<Emprestimo> findByDataDevolucaoRealIsNotNull();
    List<Emprestimo> findByAtivoTrueAndDataDevolucaoPrevistaBeforeAndDataDevolucaoRealIsNull(LocalDate data);
    List<Emprestimo> findByLeitorNomeContainingIgnoreCase(String nome);
    List<Emprestimo> findByLivroTituloContainingIgnoreCase(String titulo);

    @Query("SELECT e FROM Emprestimo e JOIN FETCH e.leitor JOIN FETCH e.livro WHERE e.ativo = true")
    List<Emprestimo> findAllAtivosWithDetails();
}
