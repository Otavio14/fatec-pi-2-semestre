package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoCategoria;
import com.fobov.fobov.repository.ProdutoCategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos-categorias")
public class ProdutoCategoriaController implements Crud<ProdutoCategoria, Integer> {
    private final ProdutoCategoriaRepository PRODUTO_CATEGORIA_REPOSITORY;

    public ProdutoCategoriaController(ProdutoCategoriaRepository PRODUTO_CATEGORIA_REPOSITORY) {
        this.PRODUTO_CATEGORIA_REPOSITORY = PRODUTO_CATEGORIA_REPOSITORY;
    }

    @GetMapping
    public List<ProdutoCategoria> findAll() {
        return PRODUTO_CATEGORIA_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public ProdutoCategoria findById(@PathVariable Integer id) {
        return PRODUTO_CATEGORIA_REPOSITORY.findById(id);
    }

    @PostMapping
    public boolean save(@RequestBody ProdutoCategoria produtoCategoria) {
        return PRODUTO_CATEGORIA_REPOSITORY.save(produtoCategoria);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody ProdutoCategoria produtoCategoria) {
        return PRODUTO_CATEGORIA_REPOSITORY.update(id, produtoCategoria);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return PRODUTO_CATEGORIA_REPOSITORY.delete(id);
    }
}
