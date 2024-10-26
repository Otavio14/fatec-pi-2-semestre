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

    @GetMapping("/{id_categoria}")
    public Categoria getCategoriaById(@PathVariable int id_categoria) {
        return CATEGORIA_REPOSITORY.findById(id_categoria);
    }

    @PostMapping
    public boolean createCategoria(@RequestBody Categoria categoria) {
        return CATEGORIA_REPOSITORY.save(categoria);
    }

    @PutMapping("/{id_categoria}")
    public boolean updateCategoria(@PathVariable int id_categoria, @RequestBody Categoria categoria) {
        return CATEGORIA_REPOSITORY.update(id_categoria, categoria);
    }

    @DeleteMapping("/{id_categoria}")
    public boolean deleteCategoria(@PathVariable int id_categoria) {
        return CATEGORIA_REPOSITORY.delete(id_categoria);
    }
}
