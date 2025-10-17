package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.LivroNaoEncontradoException;
import br.edu.infnet.wady.biblioteca.api.model.Livro;
import br.edu.infnet.wady.biblioteca.api.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro criar(@RequestBody Livro livro) {
        return livroService.criar(livro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> alterar(@PathVariable Long id, @RequestBody Livro livro) {
        Livro livroAtualizado = livroService.alterar(id, livro);
        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        livroService.excluir(id);
    }
}
