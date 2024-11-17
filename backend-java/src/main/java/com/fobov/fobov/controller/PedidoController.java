package com.fobov.fobov.controller;

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
    public List<Pedido> findAll() {
        return PEDIDO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Pedido findById(@PathVariable Integer id) {
        return PEDIDO_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Pedido pedido) {
        return PEDIDO_REPOSITORY.save(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Pedido pedido) {
        return PEDIDO_REPOSITORY.update(id, pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return PEDIDO_REPOSITORY.delete(id);
    }

    @GetMapping("/cliente/{id}")
    public List<Pedido> findAllByClienteId(@PathVariable Integer id) {
        return PEDIDO_REPOSITORY.findAllByClienteId(id);
    }
}
