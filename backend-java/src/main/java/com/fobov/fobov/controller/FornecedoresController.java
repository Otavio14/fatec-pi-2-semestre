package com.fobov.fobov.controller;

import com.fobov.fobov.model.Categoria;
import com.fobov.fobov.model.Fornecedores;
import com.fobov.fobov.repository.CategoriaRepository;
import com.fobov.fobov.repository.FornecedoresRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedoresController{
    private final FornecedoresRepository FORNECEDORES_REPOSITORY;

    public FornecedoresController(FornecedoresRepository FORNECEDORES_REPOSITORY){
        this.FORNECEDORES_REPOSITORY = FORNECEDORES_REPOSITORY;
    }

    @GetMapping
    public List<Fornecedores> getAllFornecedores(){
        return FORNECEDORES_REPOSITORY.findAll();
    }

    @GetMapping
    public Fornecedores getFornecedoresById(@PathVariable int id){
        return FORNECEDORES_REPOSITORY.findById(id);
    }

    @PostMapping
    public boolean createFornecedores(@RequestBody Fornecedores fornecedores){
        return FORNECEDORES_REPOSITORY.save(fornecedores);
    }

    @PutMapping("/{id}")
    public boolean updateFornecedores(@PathVariable int id, @RequestBody Fornecedores fornecedores){
        return FORNECEDORES_REPOSITORY.update(id, fornecedores);
    }

    @DeleteMapping("/{id}")
    public boolean deleteFornecedores(@PathVariable int id){
        return FORNECEDORES_REPOSITORY.delete(id);
    }
}