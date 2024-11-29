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

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(2)
    public List<ProdutoFornecedor> findAll() {
        return PRODUTO_FORNECEDOR_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(2)
    public ProdutoFornecedor findById(@PathVariable Integer id) {
        return PRODUTO_FORNECEDOR_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param produtoFornecedor - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(
            @RequestBody ProdutoFornecedor produtoFornecedor) {
        return PRODUTO_FORNECEDOR_REPOSITORY.save(produtoFornecedor);
    }

    /**
     * Alterar um registro
     *
     * @param id                - ID do registro
     * @param produtoFornecedor - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody
    ProdutoFornecedor produtoFornecedor) {
        return PRODUTO_FORNECEDOR_REPOSITORY.update(id, produtoFornecedor);
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    @DeleteMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return PRODUTO_FORNECEDOR_REPOSITORY.delete(id);
    }

    /**
     * Listar todos os registros com um ID de fornecedor
     *
     * @param id - ID do fornecedor
     * @return lista de registros
     */
    @GetMapping("/fornecedor/{id}")
    @RouteLevel(2)
    public List<ProdutoFornecedor> findAllByFornecedorId(
            @PathVariable Integer id) {
        return PRODUTO_FORNECEDOR_REPOSITORY.findAllByFornecedorId(id);
    }
}
