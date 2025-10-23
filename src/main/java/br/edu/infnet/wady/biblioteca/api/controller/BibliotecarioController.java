package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import br.edu.infnet.wady.biblioteca.api.service.BibliotecarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    private final BibliotecarioService bibliotecarioService;

    public BibliotecarioController(BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    @PostMapping
    public ResponseEntity<Bibliotecario> criar(@RequestBody Bibliotecario bibliotecario) {
        Bibliotecario bibliotecarioCriado = bibliotecarioService.criar(bibliotecario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bibliotecarioCriado.getId())
                .toUri();

        return ResponseEntity.created(location).body(bibliotecarioCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bibliotecario> buscarPorId(@PathVariable Long id) {
        return bibliotecarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Bibliotecário", id));
    }

    @GetMapping
    public ResponseEntity<List<Bibliotecario>> listarTodos() {
        List<Bibliotecario> bibliotecarios = bibliotecarioService.listarTodos();
        return ResponseEntity.ok(bibliotecarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bibliotecario> alterar(@PathVariable Long id,
                                                 @RequestBody Bibliotecario bibliotecario) {
        Bibliotecario bibliotecarioAtualizado = bibliotecarioService.alterar(id, bibliotecario);
        return ResponseEntity.ok(bibliotecarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        bibliotecarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
