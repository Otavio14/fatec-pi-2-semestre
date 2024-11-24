package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoFornecedor;
import com.fobov.fobov.repository.ProdutoFornecedorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores_produtos")
public class ProdutoFornecedorController
        implements Crud<ProdutoFornecedor, Integer> {
    private final ProdutoFornecedorRepository PRODUTO_FORNECEDOR_REPOSITORY;

    public ProdutoFornecedorController(
            ProdutoFornecedorRepository PRODUTO_FORNECEDOR_REPOSITORY) {
        this.PRODUTO_FORNECEDOR_REPOSITORY = PRODUTO_FORNECEDOR_REPOSITORY;
    }

    @GetMapping
    @RouteLevel(2)
    public List<ProdutoFornecedor> findAll() {
        return PRODUTO_FORNECEDOR_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    @RouteLevel(2)
    public ProdutoFornecedor findById(@PathVariable Integer id) {
        return PRODUTO_FORNECEDOR_REPOSITORY.findById(id);
    }

    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(
            @RequestBody ProdutoFornecedor produtoFornecedor) {
        return PRODUTO_FORNECEDOR_REPOSITORY.save(produtoFornecedor);
    }

    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody
    ProdutoFornecedor produtoFornecedor) {
        return PRODUTO_FORNECEDOR_REPOSITORY.update(id, produtoFornecedor);
    }

    @DeleteMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return PRODUTO_FORNECEDOR_REPOSITORY.delete(id);
    }

    @GetMapping("/fornecedor/{id}")
    @RouteLevel(2)
    public List<ProdutoFornecedor> findAllByFornecedorId(
            @PathVariable Integer id) {
        return PRODUTO_FORNECEDOR_REPOSITORY.findAllByFornecedorId(id);
    }
}
