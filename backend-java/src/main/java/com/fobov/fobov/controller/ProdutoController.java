package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Produto;
import com.fobov.fobov.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController implements Crud<Produto, Integer> {
    private final ProdutoRepository PRODUTO_REPOSITORY;

    public ProdutoController(ProdutoRepository PRODUTO_REPOSITORY) {
        this.PRODUTO_REPOSITORY = PRODUTO_REPOSITORY;
    }

    @GetMapping("/admin")
    @RouteLevel(2)
    public List<Produto> findAll() {
        return PRODUTO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    @RouteLevel(0)
    public Produto findById(@PathVariable Integer id) {
        return PRODUTO_REPOSITORY.findById(id);
    }

    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(@RequestBody Produto produto) {
        return PRODUTO_REPOSITORY.save(produto);
    }

    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Produto produto) {
        return PRODUTO_REPOSITORY.update(id, produto);
    }

    @DeleteMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return PRODUTO_REPOSITORY.delete(id);
    }

    @GetMapping
    @RouteLevel(0)
    public List<Produto> findAllAtivo() {
        return PRODUTO_REPOSITORY.findAllAtivo();
    }
}
