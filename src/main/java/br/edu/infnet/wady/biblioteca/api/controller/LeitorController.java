package br.edu.infnet.wady.biblioteca.api.controller;

import br.edu.infnet.wady.biblioteca.api.exception.PessoaNaoEncontradaException;
import br.edu.infnet.wady.biblioteca.api.model.Leitor;
import br.edu.infnet.wady.biblioteca.api.service.LeitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leitores")
public class LeitorController {

    private final LeitorService leitorService;

    public LeitorController(LeitorService leitorService) {
        this.leitorService = leitorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Leitor criar(@RequestBody Leitor leitor) {
        return leitorService.criar(leitor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leitor> buscarPorId(@PathVariable Long id) {
        return leitorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Leitor", id));
    }

    @GetMapping
    public List<Leitor> listarTodos() {
        return leitorService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leitor> alterar(@PathVariable Long id, @RequestBody Leitor leitor) {
        Leitor leitorAtualizado = leitorService.alterar(id, leitor);
        return ResponseEntity.ok(leitorAtualizado);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Leitor> inativar(@PathVariable Long id) {
        Leitor leitorInativado = leitorService.inativar(id);
        return ResponseEntity.ok(leitorInativado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        leitorService.excluir(id);
    }
}
