package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import br.edu.infnet.wady.biblioteca.api.service.BibliotecarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    private final BibliotecarioService bibliotecarioService;

    public BibliotecarioController(BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bibliotecario criar(@RequestBody Bibliotecario bibliotecario) {
        return bibliotecarioService.criar(bibliotecario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bibliotecario> buscarPorId(@PathVariable Long id) {
        return bibliotecarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Bibliotecário", id));
    }

    @GetMapping
    public List<Bibliotecario> listarTodos() {
        return bibliotecarioService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bibliotecario> alterar(@PathVariable Long id, @RequestBody Bibliotecario bibliotecario) {
        Bibliotecario bibliotecarioAtualizado = bibliotecarioService.alterar(id, bibliotecario);
        return ResponseEntity.ok(bibliotecarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bibliotecarioService.excluir(id);
    }
}
