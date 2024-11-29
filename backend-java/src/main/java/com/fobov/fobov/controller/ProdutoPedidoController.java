package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoPedido;
import com.fobov.fobov.repository.ProdutoPedidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos-pedidos")
public class ProdutoPedidoController implements Crud<ProdutoPedido, Integer> {
    private final ProdutoPedidoRepository PRODUTO_PEDIDO_REPOSITORY;

    public ProdutoPedidoController(
            ProdutoPedidoRepository PRODUTO_PEDIDO_REPOSITORY) {
        this.PRODUTO_PEDIDO_REPOSITORY = PRODUTO_PEDIDO_REPOSITORY;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(1)
    public List<ProdutoPedido> findAll() {
        return PRODUTO_PEDIDO_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(1)
    public ProdutoPedido findById(@PathVariable Integer id) {
        return PRODUTO_PEDIDO_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param produtoPedido - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(1)
    public ResponseEntity<String> save(
            @RequestBody ProdutoPedido produtoPedido) {
        return PRODUTO_PEDIDO_REPOSITORY.save(produtoPedido);
    }

    /**
     * Alterar um registro
     *
     * @param id            - ID do registro
     * @param produtoPedido - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody
    ProdutoPedido produtoPedido) {
        return PRODUTO_PEDIDO_REPOSITORY.update(id, produtoPedido);
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    @DeleteMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return PRODUTO_PEDIDO_REPOSITORY.delete(id);
    }

    /**
     * Cadastrar uma lista de registro
     *
     * @param produtosPedido - Dados dos registros
     * @return resposta da operacao
     */
    @PostMapping("/multiple")
    @RouteLevel(1)
    public ResponseEntity<String> saveMany(
            @RequestBody List<ProdutoPedido> produtosPedido) {
        return PRODUTO_PEDIDO_REPOSITORY.saveMany(produtosPedido);
    }

    /**
     * Listar todos os registros com um ID de pedido
     *
     * @param id - ID do pedido
     * @return lista de registros
     */
    @GetMapping("/pedido/{id}")
    @RouteLevel(1)
    public List<ProdutoPedido> findAllByPedidoId(@PathVariable Integer id) {
        return PRODUTO_PEDIDO_REPOSITORY.findAllByPedidoId(id);
    }
}
