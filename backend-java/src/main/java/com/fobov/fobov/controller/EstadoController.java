package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Estado;
import com.fobov.fobov.repository.EstadoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController implements Crud<Estado, Integer> {
    private final EstadoRepository ESTADO_REPOSITORY;

    public EstadoController(EstadoRepository ESTADO_REPOSITORY) {
        this.ESTADO_REPOSITORY = ESTADO_REPOSITORY;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(0)
    public List<Estado> findAll() {
        return ESTADO_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(0)
    public Estado findById(@PathVariable Integer id) {
        return ESTADO_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param estado - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(@RequestBody Estado estado) {
        return ESTADO_REPOSITORY.save(estado);
    }

    /**
     * Alterar um registro
     *
     * @param id     - ID do registro
     * @param estado - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Estado estado) {
        return ESTADO_REPOSITORY.update(id, estado);
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    @DeleteMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ESTADO_REPOSITORY.delete(id);
    }
}
