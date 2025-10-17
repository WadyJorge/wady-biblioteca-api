package br.edu.infnet.wady.biblioteca.api.exception;

public class LivroNaoEncontradoException extends EntidadeNaoEncontradaException {
    public LivroNaoEncontradoException(Long id) {
        super("Livro com ID " + id + " não encontrado.");
    }
}
