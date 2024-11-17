package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ClienteCupom;
import com.fobov.fobov.repository.ClienteCupomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes-cupons")
public class ClienteCupomController implements Crud<ClienteCupom, Integer> {
    private final ClienteCupomRepository CLIENNTE_CUPOM_REPOSITORY;

    public ClienteCupomController(ClienteCupomRepository CLIENNTE_CUPOM_REPOSITORY) {
        this.CLIENNTE_CUPOM_REPOSITORY = CLIENNTE_CUPOM_REPOSITORY;
    }

    @GetMapping
    public List<ClienteCupom> findAll() {
        return CLIENNTE_CUPOM_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public ClienteCupom findById(@PathVariable Integer id) {
        return CLIENNTE_CUPOM_REPOSITORY.findById(id);
    }

    @PostMapping
    public boolean save(@RequestBody ClienteCupom clienteCupom) {
        return CLIENNTE_CUPOM_REPOSITORY.save(clienteCupom);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody ClienteCupom clienteCupom) {
        return CLIENNTE_CUPOM_REPOSITORY.update(id, clienteCupom);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return CLIENNTE_CUPOM_REPOSITORY.delete(id);
    }
}
