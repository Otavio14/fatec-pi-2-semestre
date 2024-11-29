package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Categoria;
import com.fobov.fobov.repository.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController implements Crud<Categoria, Integer> {
    private final CategoriaRepository CATEGORIA_REPOSITORY;

    public CategoriaController(CategoriaRepository CATEGORIA_REPOSITORY) {
        this.CATEGORIA_REPOSITORY = CATEGORIA_REPOSITORY;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(1)
    public List<Categoria> findAll() {
        return CATEGORIA_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    public Categoria findById(@PathVariable Integer id) {
        return CATEGORIA_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param categoria - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Categoria categoria) {
        return CATEGORIA_REPOSITORY.save(categoria);
    }

    /**
     * Alterar um registro
     *
     * @param id        - ID do registro
     * @param categoria - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Categoria categoria) {
        return CATEGORIA_REPOSITORY.update(id, categoria);
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return CATEGORIA_REPOSITORY.delete(id);
    }
}
