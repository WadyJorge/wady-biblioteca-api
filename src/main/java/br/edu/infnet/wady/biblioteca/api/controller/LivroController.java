package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.LivroNaoEncontradoException;
import br.edu.infnet.wady.biblioteca.api.model.Livro;
import br.edu.infnet.wady.biblioteca.api.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro) {
        Livro livroCriado = livroService.criar(livro);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livroCriado.getId())
                .toUri();
        return ResponseEntity.created(location).body(livroCriado);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Livro>> listarDisponiveis() {
        return ResponseEntity.ok(livroService.listarDisponiveis());
    }

    @GetMapping("/indisponiveis")
    public ResponseEntity<List<Livro>> listarIndisponiveis() {
        return ResponseEntity.ok(livroService.listarIndisponiveis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Livro>> listarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTitulo(titulo));
    }

    @GetMapping("/buscar/autor")
    public ResponseEntity<List<Livro>> listarPorAutor(@RequestParam String autor) {
        return ResponseEntity.ok(livroService.buscarPorAutor(autor));
    }

    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<Livro>> listarPorCategoria(@RequestParam String categoria) {
        return ResponseEntity.ok(livroService.buscarPorCategoria(categoria));
    }

    @GetMapping("/buscar/periodo")
    public ResponseEntity<List<Livro>> buscarPorPeriodoPublicacao(
            @RequestParam Integer anoInicio,
            @RequestParam Integer anoFim) {
        return ResponseEntity.ok(livroService.buscarPorPeriodoPublicacao(anoInicio, anoFim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> alterar(@PathVariable Long id,
                                         @Valid @RequestBody Livro livro) {
        return ResponseEntity.ok(livroService.alterar(id, livro));
    }

    @PatchMapping("/{id}/disponivel")
    public ResponseEntity<Livro> marcarComoDisponivel(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.marcarDisponivel(id));
    }

    @PatchMapping("/{id}/indisponivel")
    public ResponseEntity<Livro> marcarComoIndisponivel(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.marcarIndisponivel(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
