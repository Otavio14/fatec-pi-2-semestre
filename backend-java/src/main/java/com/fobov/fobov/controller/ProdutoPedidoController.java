package com.fobov.fobov.controller;

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

    @GetMapping
    public List<ProdutoPedido> findAll() {
        return PRODUTO_PEDIDO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public ProdutoPedido findById(@PathVariable Integer id) {
        return PRODUTO_PEDIDO_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(
            @RequestBody ProdutoPedido produtoPedido) {
        return PRODUTO_PEDIDO_REPOSITORY.save(produtoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody
    ProdutoPedido produtoPedido) {
        return PRODUTO_PEDIDO_REPOSITORY.update(id, produtoPedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return PRODUTO_PEDIDO_REPOSITORY.delete(id);
    }
}
