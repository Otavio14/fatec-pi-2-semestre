package com.fobov.fobov.interfaces;

public interface Crud<T, ID> {
    Iterable<T> findAll();

    T findById(ID id);

    boolean save(T entity);

    boolean update(ID id, T entity);

    boolean delete(ID id);
}
