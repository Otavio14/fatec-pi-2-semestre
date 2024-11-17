package com.fobov.fobov.controller;

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

    @GetMapping
    public List<Fornecedor> findAll() {
        return FORNECEDORES_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Fornecedor findById(@PathVariable Integer id) {
        return FORNECEDORES_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Fornecedor fornecedor) {
        return FORNECEDORES_REPOSITORY.save(fornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Fornecedor fornecedor) {
        return FORNECEDORES_REPOSITORY.update(id, fornecedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return FORNECEDORES_REPOSITORY.delete(id);
    }
}