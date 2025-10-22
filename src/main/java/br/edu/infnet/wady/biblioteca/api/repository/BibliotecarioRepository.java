package br.edu.infnet.wady.biblioteca.api.repository;

import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
}
