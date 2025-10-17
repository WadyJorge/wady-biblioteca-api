package br.edu.infnet.wady.biblioteca.api.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {

    T criar(T entity);
    Optional<T> buscarPorId(ID id);
    List<T> listarTodos();
    T alterar(ID id, T entity);
    void excluir(ID id);
}
