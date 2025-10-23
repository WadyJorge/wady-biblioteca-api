package br.edu.infnet.wady.biblioteca.api.repository;

import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {

    @Query("SELECT b FROM Bibliotecario b JOIN FETCH b.endereco")
    List<Bibliotecario> findAllWithEndereco();
}
