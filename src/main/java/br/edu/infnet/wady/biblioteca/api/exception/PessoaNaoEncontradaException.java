package br.edu.infnet.wady.biblioteca.api.exception;

public class PessoaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public PessoaNaoEncontradaException(String tipo, Long id) {
        super(tipo + " com ID: " + id + " não encontrado(a).");
    }
}
