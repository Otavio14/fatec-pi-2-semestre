package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ClienteCupom;
import com.fobov.fobov.repository.ClienteCupomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes-cupons")
public class ClienteCupomController implements Crud<ClienteCupom, Integer> {
    private final ClienteCupomRepository CLIENNTE_CUPOM_REPOSITORY;

    public ClienteCupomController(
            ClienteCupomRepository CLIENNTE_CUPOM_REPOSITORY) {
        this.CLIENNTE_CUPOM_REPOSITORY = CLIENNTE_CUPOM_REPOSITORY;
    }

    @GetMapping
    @RouteLevel(1)
    public List<ClienteCupom> findAll() {
        return CLIENNTE_CUPOM_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    @RouteLevel(1)
    public ClienteCupom findById(@PathVariable Integer id) {
        return CLIENNTE_CUPOM_REPOSITORY.findById(id);
    }

    @PostMapping
    @RouteLevel(1)
    public ResponseEntity<String> save(@RequestBody ClienteCupom clienteCupom) {
        return CLIENNTE_CUPOM_REPOSITORY.save(clienteCupom);
    }

    @PutMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody
    ClienteCupom clienteCupom) {
        return CLIENNTE_CUPOM_REPOSITORY.update(id, clienteCupom);
    }

    @DeleteMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return CLIENNTE_CUPOM_REPOSITORY.delete(id);
    }

    @PostMapping("/cliente/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> saveByClienteId(
            @RequestBody ClienteCupom clienteCupom, @PathVariable Integer id) {
        return CLIENNTE_CUPOM_REPOSITORY.saveByClienteId(clienteCupom, id);
    }
}
