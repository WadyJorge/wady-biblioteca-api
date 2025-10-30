package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.service.LeitorService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/leitores")
public class LeitorController {

    private final LeitorService leitorService;

    public LeitorController(LeitorService leitorService) {
        this.leitorService = leitorService;
    }

    @PostMapping
    public ResponseEntity<Leitor> criar(@Valid @RequestBody Leitor leitor) {
        Leitor leitorCriado = leitorService.criar(leitor);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(leitorCriado.getId())
                .toUri();
        return ResponseEntity.created(location).body(leitorCriado);
    }

    @GetMapping
    public ResponseEntity<List<Leitor>> listarTodos() {
        return ResponseEntity.ok(leitorService.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Leitor>> listarAtivos() {
        return ResponseEntity.ok(leitorService.listarAtivos());
    }

    @GetMapping("/inativos")
    public ResponseEntity<List<Leitor>> listarInativos() {
        return ResponseEntity.ok(leitorService.listarInativos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leitor> buscarPorId(@PathVariable Long id) {
        return leitorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Leitor", id));
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Leitor>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(leitorService.buscarPorNome(nome));
    }

    @GetMapping("/buscar/cpf")
    public ResponseEntity<Leitor> buscarPorCpf(@RequestParam String cpf) {
        return leitorService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/matricula")
    public ResponseEntity<Leitor> buscarPorMatricula(@RequestParam String matricula) {
        return leitorService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/periodo")
    public ResponseEntity<List<Leitor>> buscarPorPeriodoInscricao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(leitorService.buscarPorPeriodoInscricao(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leitor> alterar(@PathVariable Long id,
                                          @Valid @RequestBody Leitor leitor) {
        return ResponseEntity.ok(leitorService.alterar(id, leitor));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Leitor> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(leitorService.inativar(id));
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Leitor> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(leitorService.reativar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        leitorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
