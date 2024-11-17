package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cliente;
import com.fobov.fobov.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController implements Crud<Cliente, Integer> {
    private final ClienteRepository CLIENTE_REPOSITORY;

    public ClienteController(ClienteRepository CLIENTE_REPOSITORY) {
        this.CLIENTE_REPOSITORY = CLIENTE_REPOSITORY;
    }

    @GetMapping
    public List<Cliente> findAll() {
        return CLIENTE_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Integer id) {
        return CLIENTE_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Cliente cliente) {
        return CLIENTE_REPOSITORY.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Cliente cliente) {
        return CLIENTE_REPOSITORY.update(id, cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return CLIENTE_REPOSITORY.delete(id);
    }
}
