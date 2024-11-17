package com.fobov.fobov.interfaces;

import org.springframework.http.ResponseEntity;

public interface Crud<T, ID> {
    Iterable<T> findAll();

    T findById(ID id);

    ResponseEntity<String> save(T entity);

    ResponseEntity<String> update(ID id, T entity);

    ResponseEntity<String> delete(ID id);
}
