package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Fornecedor;
import com.fobov.fobov.repository.FornecedorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController implements Crud<Fornecedor, Integer> {
    private final FornecedorRepository FORNECEDORES_REPOSITORY;

    public FornecedorController(FornecedorRepository FORNECEDORES_REPOSITORY) {
        this.FORNECEDORES_REPOSITORY = FORNECEDORES_REPOSITORY;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(2)
    public List<Fornecedor> findAll() {
        return FORNECEDORES_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(2)
    public Fornecedor findById(@PathVariable Integer id) {
        return FORNECEDORES_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param fornecedor - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(@RequestBody Fornecedor fornecedor) {
        return FORNECEDORES_REPOSITORY.save(fornecedor);
    }

    /**
     * Alterar um registro
     *
     * @param id         - ID do registro
     * @param fornecedor - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Fornecedor fornecedor) {
        return FORNECEDORES_REPOSITORY.update(id, fornecedor);
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
        return FORNECEDORES_REPOSITORY.delete(id);
    }
}