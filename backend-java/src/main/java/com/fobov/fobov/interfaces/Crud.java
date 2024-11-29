package com.fobov.fobov.interfaces;

import org.springframework.http.ResponseEntity;

/**
 * Interface que representa a nomenclatura padrão para todas as requisições
 *
 * @param <T>  - Tipo que a classe utiliza
 * @param <ID> - Tipo do ID que a classe utiliza
 */
public interface Crud<T, ID> {
    /**
     * Listagem de todos os registros
     *
     * @return lista com os dados
     */
    Iterable<T> findAll();

    /**
     * Dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    T findById(ID id);

    /**
     * Ação de salvar um registro novo
     *
     * @param entity - Dados do registro
     * @return mensagem sobre a operacão
     */
    ResponseEntity<String> save(T entity);

    /**
     * Ação de alterar um registro
     *
     * @param id     - ID do registro
     * @param entity - Dados do registro
     * @return mensagem sobre a operacão
     */
    ResponseEntity<String> update(ID id, T entity);

    /**
     * Ação de remover um registro
     *
     * @param id - ID do registro
     * @return mensagem sobre a operacão
     */
    ResponseEntity<String> delete(ID id);
}
