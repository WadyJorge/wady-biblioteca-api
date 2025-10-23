package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.service.LeitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/leitores")
public class LeitorController {

    private final LeitorService leitorService;

    public LeitorController(LeitorService leitorService) {
        this.leitorService = leitorService;
    }

    @PostMapping
    public ResponseEntity<Leitor> criar(@RequestBody Leitor leitor) {
        Leitor leitorCriado = leitorService.criar(leitor);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(leitorCriado.getId())
                .toUri();

        return ResponseEntity.created(location).body(leitorCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leitor> buscarPorId(@PathVariable Long id) {
        return leitorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Leitor", id));
    }

    @GetMapping
    public ResponseEntity<List<Leitor>> listarTodos() {
        List<Leitor> leitores = leitorService.listarTodos();
        return ResponseEntity.ok(leitores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leitor> alterar(@PathVariable Long id,
                                          @RequestBody Leitor leitor) {
        Leitor leitorAtualizado = leitorService.alterar(id, leitor);
        return ResponseEntity.ok(leitorAtualizado);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Leitor> inativar(@PathVariable Long id) {
        Leitor leitorInativado = leitorService.inativar(id);
        return ResponseEntity.ok(leitorInativado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        leitorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
