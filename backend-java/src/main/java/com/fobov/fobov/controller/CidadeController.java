package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cidade;
import com.fobov.fobov.repository.CidadeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController implements Crud<Cidade, Integer> {
    private final CidadeRepository CIDADE_REPOSITORY;

    public CidadeController(CidadeRepository CIDADE_REPOSITORY) {
        this.CIDADE_REPOSITORY = CIDADE_REPOSITORY;
    }

    @GetMapping
    public List<Cidade> findAll() {
        return CIDADE_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Cidade findById(@PathVariable Integer id) {
        return CIDADE_REPOSITORY.findById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Cidade cidade) {
        return CIDADE_REPOSITORY.save(cidade);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Cidade cidade) {
        return CIDADE_REPOSITORY.update(id, cidade);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return CIDADE_REPOSITORY.delete(id);
    }
}
