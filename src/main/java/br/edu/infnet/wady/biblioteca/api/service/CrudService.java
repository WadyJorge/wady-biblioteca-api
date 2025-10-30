package br.edu.infnet.wady.biblioteca.api.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {

    T criar(T entity);
    List<T> listarTodos();
    Optional<T> buscarPorId(ID id);
    T alterar(ID id, T entity);
    void excluir(ID id);
}
