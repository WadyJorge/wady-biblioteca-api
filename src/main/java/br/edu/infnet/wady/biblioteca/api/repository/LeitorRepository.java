package br.edu.infnet.wady.biblioteca.api.repository;

import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    List<Leitor> findByAtivoTrue();
    List<Leitor> findByAtivoFalse();
    List<Leitor> findByNomeContainingIgnoreCase(String nome);
    Optional<Leitor> findByCpf(String cpf);
    Optional<Leitor> findByMatricula(String matricula);
    List<Leitor> findByDataInscricaoBetween(LocalDate inicio, LocalDate fim);
}
