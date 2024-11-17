package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Categoria;
import com.fobov.fobov.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController implements Crud<Categoria, Integer> {
    private final CategoriaRepository CATEGORIA_REPOSITORY;

    public CategoriaController(CategoriaRepository CATEGORIA_REPOSITORY) {
        this.CATEGORIA_REPOSITORY = CATEGORIA_REPOSITORY;
    }

    @GetMapping
    public List<Categoria> findAll() {
        return CATEGORIA_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Categoria findById(@PathVariable Integer id) {
        return CATEGORIA_REPOSITORY.findById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Categoria categoria) {
        return CATEGORIA_REPOSITORY.save(categoria);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Categoria categoria) {
        return CATEGORIA_REPOSITORY.update(id, categoria);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return CATEGORIA_REPOSITORY.delete(id);
    }
}
