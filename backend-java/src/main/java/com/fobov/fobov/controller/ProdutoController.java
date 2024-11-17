package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Produto;
import com.fobov.fobov.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController implements Crud<Produto, Integer> {
    private final ProdutoRepository PRODUTO_REPOSITORY;

    public ProdutoController(ProdutoRepository PRODUTO_REPOSITORY) {
        this.PRODUTO_REPOSITORY = PRODUTO_REPOSITORY;
    }

    @GetMapping
    public List<Produto> findAll() {
        return PRODUTO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Produto findById(@PathVariable Integer id) {
        return PRODUTO_REPOSITORY.findById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Produto produto) {
        return PRODUTO_REPOSITORY.save(produto);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Produto produto) {
        return PRODUTO_REPOSITORY.update(id, produto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return PRODUTO_REPOSITORY.delete(id);
    }
}
