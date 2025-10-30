package br.edu.infnet.wady.biblioteca.api.repository;

import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {

    List<Bibliotecario> findByNomeContainingIgnoreCase(String nome);
    Optional<Bibliotecario> findByCpf(String cpf);
    Optional<Bibliotecario> findByEmail(String email);
    Optional<Bibliotecario> findByMatricula(String matricula);
    List<Bibliotecario> findBySalarioBetween(BigDecimal salarioMin, BigDecimal salarioMax);

    @Query("SELECT b FROM Bibliotecario b JOIN FETCH b.endereco")
    List<Bibliotecario> findAllWithEndereco();
}
