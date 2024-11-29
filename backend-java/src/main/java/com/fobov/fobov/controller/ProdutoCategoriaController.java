package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoCategoria;
import com.fobov.fobov.repository.ProdutoCategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos-categorias")
public class ProdutoCategoriaController
        implements Crud<ProdutoCategoria, Integer> {
    private final ProdutoCategoriaRepository PRODUTO_CATEGORIA_REPOSITORY;

    public ProdutoCategoriaController(
            ProdutoCategoriaRepository PRODUTO_CATEGORIA_REPOSITORY) {
        this.PRODUTO_CATEGORIA_REPOSITORY = PRODUTO_CATEGORIA_REPOSITORY;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(2)
    public List<ProdutoCategoria> findAll() {
        return PRODUTO_CATEGORIA_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(2)
    public ProdutoCategoria findById(@PathVariable Integer id) {
        return PRODUTO_CATEGORIA_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param produtoCategoria - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(
            @RequestBody ProdutoCategoria produtoCategoria) {
        return PRODUTO_CATEGORIA_REPOSITORY.save(produtoCategoria);
    }

    /**
     * Alterar um registro
     *
     * @param id               - ID do registro
     * @param produtoCategoria - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody
    ProdutoCategoria produtoCategoria) {
        return PRODUTO_CATEGORIA_REPOSITORY.update(id, produtoCategoria);
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
        return PRODUTO_CATEGORIA_REPOSITORY.delete(id);
    }

    /**
     * Listar todos os registros com um ID de produto
     *
     * @param id - ID do produto
     * @return lista de registros
     */
    @GetMapping("/produto/{id}")
    @RouteLevel(1)
    public List<ProdutoCategoria> findAllByProdutoId(@PathVariable Integer id) {
        return PRODUTO_CATEGORIA_REPOSITORY.findAllByProdutoId(id);
    }
}
