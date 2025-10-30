package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Bibliotecario;
import br.edu.infnet.wady.biblioteca.api.service.BibliotecarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
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
    public ResponseEntity<Bibliotecario> criar(@Valid @RequestBody Bibliotecario bibliotecario) {
        Bibliotecario bibliotecarioCriado = bibliotecarioService.criar(bibliotecario);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bibliotecarioCriado.getId())
                .toUri();
        return ResponseEntity.created(location).body(bibliotecarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<Bibliotecario>> listarTodos() {
        return ResponseEntity.ok(bibliotecarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bibliotecario> buscarPorId(@PathVariable Long id) {
        return bibliotecarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Bibliotecário", id));
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Bibliotecario>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorNome(nome));
    }

    @GetMapping("/buscar/cpf")
    public ResponseEntity<Bibliotecario> buscarPorCpf(@RequestParam String cpf) {
        return bibliotecarioService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/email")
    public ResponseEntity<Bibliotecario> buscarPorEmail(@RequestParam String email) {
        return bibliotecarioService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/matricula")
    public ResponseEntity<Bibliotecario> buscarPorMatricula(@RequestParam String matricula) {
        return bibliotecarioService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/salario")
    public ResponseEntity<List<Bibliotecario>> buscarPorFaixaSalarial(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorFaixaSalarial(min, max));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bibliotecario> alterar(@PathVariable Long id,
                                                 @Valid @RequestBody Bibliotecario bibliotecario) {
        return ResponseEntity.ok(bibliotecarioService.alterar(id, bibliotecario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        bibliotecarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
