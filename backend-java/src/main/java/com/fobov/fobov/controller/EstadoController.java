package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
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
    @RouteLevel(0)
    public List<Estado> findAll() {
        return ESTADO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    @RouteLevel(0)
    public Estado findById(@PathVariable Integer id) {
        return ESTADO_REPOSITORY.findById(id);
    }

    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(@RequestBody Estado estado) {
        return ESTADO_REPOSITORY.save(estado);
    }

    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Estado estado) {
        return ESTADO_REPOSITORY.update(id, estado);
    }

    @DeleteMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ESTADO_REPOSITORY.delete(id);
    }
}
