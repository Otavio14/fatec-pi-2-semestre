package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Pedido;
import com.fobov.fobov.repository.PedidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements Crud<Pedido, Integer> {
    private final PedidoRepository PEDIDO_REPOSITORY;

    public PedidoController(PedidoRepository PEDIDO_REPOSITORY) {
        this.PEDIDO_REPOSITORY = PEDIDO_REPOSITORY;
    }

    @GetMapping
    @RouteLevel(2)
    public List<Pedido> findAll() {
        return PEDIDO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    @RouteLevel(1)
    public Pedido findById(@PathVariable Integer id) {
        return PEDIDO_REPOSITORY.findById(id);
    }

    @PostMapping
    @RouteLevel(1)
    public ResponseEntity<String> save(@RequestBody Pedido pedido) {
        return PEDIDO_REPOSITORY.save(pedido);
    }

    @PutMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Pedido pedido) {
        return PEDIDO_REPOSITORY.update(id, pedido);
    }

    @DeleteMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return PEDIDO_REPOSITORY.delete(id);
    }

    @GetMapping("/cliente/{id}")
    @RouteLevel(1)
    public List<Pedido> findAllByClienteId(@PathVariable Integer id) {
        return PEDIDO_REPOSITORY.findAllByClienteId(id);
    }
}
