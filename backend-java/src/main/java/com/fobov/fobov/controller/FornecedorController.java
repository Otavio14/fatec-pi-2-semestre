package com.fobov.fobov.controller;

import com.fobov.fobov.model.Fornecedor;
import com.fobov.fobov.repository.FornecedorRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
    private final FornecedorRepository FORNECEDORES_REPOSITORY;

    public FornecedorController(FornecedorRepository FORNECEDORES_REPOSITORY){
        this.FORNECEDORES_REPOSITORY = FORNECEDORES_REPOSITORY;
    }

    @GetMapping
    public List<Fornecedor> getAllFornecedores(){
        return FORNECEDORES_REPOSITORY.findAll();
    }

    @GetMapping("/{id_fornecedores}")
    public Fornecedor getFornecedoresById(@PathVariable int id_fornecedores){
        return FORNECEDORES_REPOSITORY.findById(id_fornecedores);
    }

    @PostMapping
    public boolean createFornecedores(@RequestBody Fornecedor fornecedor){
        return FORNECEDORES_REPOSITORY.save(fornecedor);
    }

    @PutMapping("/{id_fornecedores}")
    public boolean updateFornecedores(@PathVariable int id_fornecedores, @RequestBody Fornecedor fornecedor){
        return FORNECEDORES_REPOSITORY.update(id_fornecedores, fornecedor);
    }

    @DeleteMapping("/{id_fornecedores}")
    public boolean deleteFornecedores(@PathVariable int id_fornecedores){
        return FORNECEDORES_REPOSITORY.delete(id_fornecedores);
    }
}