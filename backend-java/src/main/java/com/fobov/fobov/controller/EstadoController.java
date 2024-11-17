package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Estado;
import com.fobov.fobov.repository.EstadoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController implements Crud<Estado, Integer> {
    private final EstadoRepository ESTADO_REPOSITORY;

    public EstadoController(EstadoRepository ESTADO_REPOSITORY) {
        this.ESTADO_REPOSITORY = ESTADO_REPOSITORY;
    }

    @GetMapping
    public List<Estado> findAll() {
        return ESTADO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Estado findById(@PathVariable Integer id) {
        return ESTADO_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Estado estado) {
        return ESTADO_REPOSITORY.save(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Estado estado) {
        return ESTADO_REPOSITORY.update(id, estado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ESTADO_REPOSITORY.delete(id);
    }
}
