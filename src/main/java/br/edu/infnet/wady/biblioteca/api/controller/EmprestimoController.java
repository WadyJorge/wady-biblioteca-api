package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.EmprestimoNaoEncontradoException;
import br.edu.infnet.wady.biblioteca.api.model.Emprestimo;
import br.edu.infnet.wady.biblioteca.api.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<Emprestimo> criar(@Valid @RequestBody Emprestimo emprestimo) {
        Emprestimo emprestimoCriado = emprestimoService.criar(emprestimo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprestimoCriado.getId())
                .toUri();
        return ResponseEntity.created(location).body(emprestimoCriado);
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Emprestimo>> listarAtivos() {
        return ResponseEntity.ok(emprestimoService.listarAtivos());
    }

    @GetMapping("/devolvidos")
    public ResponseEntity<List<Emprestimo>> listarDevolvidos() {
        return ResponseEntity.ok(emprestimoService.listarDevolvidos());
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<Emprestimo>> listarAtrasados() {
        return ResponseEntity.ok(emprestimoService.listarAtrasados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        return emprestimoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EmprestimoNaoEncontradoException(id));
    }

    @GetMapping("/buscar/leitor")
    public ResponseEntity<List<Emprestimo>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(emprestimoService.buscarPorNome(nome));
    }

    @GetMapping("/buscar/livro")
    public ResponseEntity<List<Emprestimo>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(emprestimoService.buscarPorTitulo(titulo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> alterar(@PathVariable Long id,
                                              @Valid @RequestBody Emprestimo emprestimo) {
        return ResponseEntity.ok(emprestimoService.alterar(id, emprestimo));
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<Emprestimo> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.devolver(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        emprestimoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
