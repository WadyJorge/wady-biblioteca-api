package br.edu.infnet.wady.biblioteca.api.exception;

public class EmprestimoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public EmprestimoNaoEncontradoException(Long id) {
        super("Empréstimo com ID: " + id + " não encontrado.");
    }
}
