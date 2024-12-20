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

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping("/admin")
    @RouteLevel(2)
    public List<Produto> findAll() {
        return PRODUTO_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(0)
    public Produto findById(@PathVariable Integer id) {
        return PRODUTO_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param produto - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(@RequestBody Produto produto) {
        return PRODUTO_REPOSITORY.save(produto);
    }

    /**
     * Alterar um registro
     *
     * @param id      - ID do registro
     * @param produto - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Produto produto) {
        return PRODUTO_REPOSITORY.update(id, produto);
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
        return PRODUTO_REPOSITORY.delete(id);
    }

    /**
     * Listar todos os registros ativos
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(0)
    public List<Produto> findAllAtivo() {
        return PRODUTO_REPOSITORY.findAllAtivo();
    }
}
