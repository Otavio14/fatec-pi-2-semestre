package com.fobov.fobov.controller;

import com.fobov.fobov.model.Categoria;
import com.fobov.fobov.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaRepository CATEGORIA_REPOSITORY;

    public CategoriaController(CategoriaRepository CATEGORIA_REPOSITORY) {
        this.CATEGORIA_REPOSITORY = CATEGORIA_REPOSITORY;
    }

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return CATEGORIA_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Categoria getCategoriaById(@PathVariable int id) {
        return CATEGORIA_REPOSITORY.findById(id);
    }

    @PostMapping
    public boolean createCategoria(@RequestBody Categoria categoria) {
        return CATEGORIA_REPOSITORY.save(categoria);
    }

    @PutMapping("/{id}")
    public boolean updateCategoria(@PathVariable int id, @RequestBody Categoria categoria) {
        return CATEGORIA_REPOSITORY.update(id, categoria);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCategoria(@PathVariable int id) {
        return CATEGORIA_REPOSITORY.delete(id);
    }
}
